package com.pgf.protocol.codec;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/10 16:54
 * @description:泛型是子类的类型
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(MyInterface.class);
        System.out.println(Impl.class);
    }

    public static interface MyInterface{

    }

    public static class Impl implements MyInterface{

    }
}
