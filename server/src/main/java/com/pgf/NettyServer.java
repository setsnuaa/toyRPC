package com.pgf;

import com.pgf.anotations.RpcScan;
import com.pgf.config.RpcServiceConfig;
import com.pgf.impl.HelloServiceImpl;
import com.pgf.protocol.transport.netty.server.NettyRpcServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/19 15:09
 * @description
 */
@RpcScan(basePackage = {"com.pgf"})
public class NettyServer {
    public static void main(String[] args) {
        // 通过注解注册服务
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServer.class);
        NettyRpcServer nettyRpcServer = (NettyRpcServer) applicationContext.getBean("nettyRpcServer");

        HelloService helloService = new HelloServiceImpl();
        // 将实现类包装成rpc service
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                .group("test1").version("version1").service(helloService).build();
        nettyRpcServer.registeredService(rpcServiceConfig);
        nettyRpcServer.start();
    }
}
