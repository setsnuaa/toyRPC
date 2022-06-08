package com.pgf.proxy;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/8 17:27
 * @description
 */
public class MyInterfaceImpl implements MyInterface {
    @Override
    public void printHello() {
        System.out.println("hello");
    }

    @Override
    public void printWorld() {
        System.out.println("world");
    }

    @Override
    public String helloWorld() {
        return "helloWorld";
    }
}
