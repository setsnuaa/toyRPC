package com.pgf.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/8 17:27
 * @description
 */
public class DynamicProxy implements InvocationHandler {
    private final Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始代理");
        Object result = method.invoke(target, args);
        System.out.println("代理结束");
        return result;
    }
}
