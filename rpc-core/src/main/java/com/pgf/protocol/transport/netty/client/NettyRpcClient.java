package com.pgf.protocol.transport.netty.client;

import com.pgf.enums.CompressTypeEnum;
import com.pgf.enums.SerializationTypeEnum;
import com.pgf.extension.ExtensionLoader;
import com.pgf.factory.SingletonFactory;
import com.pgf.protocol.constants.RpcConstants;
import com.pgf.protocol.dto.RpcMessage;
import com.pgf.protocol.dto.RpcRequest;
import com.pgf.protocol.dto.RpcResponse;
import com.pgf.protocol.transport.RpcRequestTransport;
import com.pgf.protocol.transport.netty.codec.RpcMessageDecoder;
import com.pgf.protocol.transport.netty.codec.RpcMessageEncoder;
import com.pgf.registry.ServiceDiscovery;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/19 16:14
 * @description: netty客户端用于建立和netty服务端的网络连接、发送rpc请求
 */
@Slf4j
public final class NettyRpcClient implements RpcRequestTransport {
    private final ServiceDiscovery serviceDiscovery;
    private final UnprocessedRequests unprocessedRequests;
    private final ChannelProvider channelProvider;
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;

    public NettyRpcClient() {
        this.eventLoopGroup = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                //in or out
                                .addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS))
                                //out
                                .addLast(new RpcMessageEncoder())
                                //in
                                .addLast(new RpcMessageDecoder())
                                //in
                                .addLast(new NettyRpcClientHandler());
                    }
                });
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("zk");
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
        this.channelProvider = SingletonFactory.getInstance(ChannelProvider.class);
    }

    /**
     * 连接服务端，拿到和服务端通信的channel
     *
     * @param inetSocketAddress
     * @return
     */
    @SneakyThrows
    public Channel doConnect(InetSocketAddress inetSocketAddress) {
        CompletableFuture<Channel> completableFuture = new CompletableFuture<>();
        bootstrap.connect(inetSocketAddress)
                //回调函数，当connect完成时会执行该方法，注意是执行connect的线程执行该方法
                .addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        log.info("The client connected [{}] successful!", inetSocketAddress.toString());
                        completableFuture.complete(future.channel());
                    } else {
                        throw new IllegalStateException();
                    }
                });
        return completableFuture.get();
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        //创建一个返回对象
        CompletableFuture<RpcResponse<Object>> resultFuture = new CompletableFuture<>();
        //获取服务端地址
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest);
        //获取和服务端通信的channel
        Channel channel = getChannel(inetSocketAddress);

        if (channel.isActive()) {
            //由于客户端是异步处理请求，不知道会不会收到服务端相应，所以先添加到未处理map中
            unprocessedRequests.put(rpcRequest.getRequestId(), resultFuture);

            RpcMessage rpcMessage = RpcMessage.builder()
                    .messageType(RpcConstants.REQUEST_TYPE)
                    .compressType(CompressTypeEnum.SNAPPY.getCode())
                    .serializeType(SerializationTypeEnum.PROTOSTUFF.getCode())
                    .data(rpcRequest).build();

            channel.writeAndFlush(rpcMessage).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    log.info("client send message: [{}]", rpcMessage);
                } else {
                    future.channel().close();
                    resultFuture.completeExceptionally(future.cause());
                    log.error("send failed:", future.cause());
                }
            });
        } else {
            throw new IllegalStateException();
        }

        return resultFuture;
    }

    public Channel getChannel(InetSocketAddress inetSocketAddress) {
        Channel channel = channelProvider.get(inetSocketAddress);
        if (channel == null) {
            channel = doConnect(inetSocketAddress);
            channelProvider.set(inetSocketAddress, channel);
        }
        return channel;
    }

    public void close() {
        eventLoopGroup.shutdownGracefully();
    }
}
