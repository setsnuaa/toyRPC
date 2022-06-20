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
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClient.class);
        HelloController helloController=(HelloController) applicationContext.getBean("helloController");
        helloController.test();
    }
}
