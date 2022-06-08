package com.pgf.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/8 17:29
 * @description
 */
public class Main {
    public static void main(String[] args) {
        MyInterface myInterface = new MyInterfaceImpl();
        InvocationHandler handler = new DynamicProxy(myInterface);
        MyInterface proxy = (MyInterface) Proxy.newProxyInstance(myInterface.getClass().getClassLoader(), myInterface.getClass().getInterfaces(), handler);
        proxy.printHello();
        proxy.printWorld();
        System.out.println(proxy.helloWorld());
    }
    //开始代理
    //hello
    //代理结束
    //开始代理
    //world
    //代理结束
    //开始代理
    //代理结束
    //helloWorld
}
