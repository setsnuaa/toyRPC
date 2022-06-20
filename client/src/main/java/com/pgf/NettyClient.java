package com.pgf;

import com.pgf.anotations.RpcScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/19 15:23
 * @description
 */
@RpcScan(basePackage = {"com.pgf"})
public class NettyClient {
    public static void main(String[] args) {
        // 容器启动之后先把单例模式对象全部实例化，这个时候就已经用动态代理对象作为HelloService的bean了
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClient.class);
        HelloController helloController=(HelloController) applicationContext.getBean("helloController");
        helloController.test();
    }
}
