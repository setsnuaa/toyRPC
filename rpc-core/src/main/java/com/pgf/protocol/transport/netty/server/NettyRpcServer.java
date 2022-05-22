package com.pgf.protocol.transport.netty.server;

import com.pgf.config.CustomShutdownHook;
import com.pgf.config.RpcServiceConfig;
import com.pgf.factory.SingletonFactory;
import com.pgf.protocol.transport.netty.codec.RpcMessageDecoder;
import com.pgf.protocol.transport.netty.codec.RpcMessageEncoder;
import com.pgf.provider.ServiceProvider;
import com.pgf.provider.impl.ZkServiceProviderImpl;
import com.pgf.util.RuntimeUtil;
import com.pgf.util.concurrent.threadpool.ThreadPoolFactoryUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/21 16:05
 * @description:
 */
@Slf4j
@Component
public class NettyRpcServer {

    public static final int PORT = 9998;

    private final ServiceProvider serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);

    public void registeredService(RpcServiceConfig rpcServiceConfig) {
        serviceProvider.publishService(rpcServiceConfig);
    }

    @SneakyThrows
    public void start() {
        CustomShutdownHook.getCustomShutdownHook().clearAll();
        String host = InetAddress.getLocalHost().getHostAddress();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        DefaultEventExecutorGroup serviceHandlerGroup = new DefaultEventExecutorGroup(
                RuntimeUtil.cpus() * 2,
                ThreadPoolFactoryUtil.createThreadFactory("service-handler-group", false)
        );
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //childOption对worker设置，option对boss设置
                    //TCP默认开启了 Nagle 算法，该算法的作用是尽可能的发送大数据快，减少网络传输，把它关了，不然有延迟
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //开启 TCP 底层心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //TCP有全连接队列和半连接队列
                    //这里表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<ServerChannel>() {
                        @Override
                        protected void initChannel(ServerChannel serverChannel) throws Exception {
                            serverChannel.pipeline()
                                    //in
                                    .addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS))
                                    //in
                                    .addLast(new RpcMessageDecoder())
                                    //out
                                    .addLast(new RpcMessageEncoder())
                                    //in
                                    .addLast(serviceHandlerGroup, new NettyRpcServerHandler());
                        }
                    });
            //等待服务端绑定端口
            ChannelFuture future = bootstrap.bind(host, PORT).sync();
            //等待服务端用于监听的channel关闭
            //当前线程会阻塞，但是bossGroup、workerGroup和handlerGroup这3个线程池会正常运行
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("occur exception when start server:", e);
        } finally {
            log.error("shutdown bossGroup and workerGroup");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            serviceHandlerGroup.shutdownGracefully();
        }
    }
}
