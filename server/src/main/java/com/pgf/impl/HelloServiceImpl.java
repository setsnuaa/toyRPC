package com.pgf.impl;

import com.pgf.Hello;
import com.pgf.HelloService;
import com.pgf.anotations.RpcService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/19 15:03
 * @description
 */
@Slf4j
@RpcService(group = "test1", version = "version1")
public class HelloServiceImpl implements HelloService {
    static {
        System.out.println("HelloServiceImpl被创建");
    }

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl返回: {}.", result);
        return result;
    }
}
