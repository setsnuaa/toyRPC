package com.pgf.protocol.transport.netty.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/10 14:51
 * @description 用来获得异步调用结果
 */
public class CompleteFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            System.out.println("线程启动");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("唤醒主线程");
            future.complete("hello");
        }).start();
        System.out.println("等待线程执行完毕");
        System.out.println(future.get());
    }
}
