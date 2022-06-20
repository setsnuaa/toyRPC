package com.pgf;

import com.pgf.anotations.RpcReference;
import org.springframework.stereotype.Component;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/19 15:19
 * @description
 */
@Component
public class HelloController {

    @RpcReference(group = "test1", version = "version1")
    private HelloService helloService;

    public void test() {
//        String hello=this.helloService.hello(new Hello("111","hello world"));
//        Thread.sleep(1000);
        for (int i = 0; i < 10; i++) {
            //System.out.println(helloService.getClass().getName()); //com.sun.proxy.$Proxy11
            System.out.println(helloService.hello(new Hello("111", "hello world")));
        }
    }
}
