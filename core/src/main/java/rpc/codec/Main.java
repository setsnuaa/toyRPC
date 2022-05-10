package rpc.codec;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/10 16:54
 * @description:泛型是子类的类型
 */
public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        System.out.println(getclass(list));
        list=new LinkedList<>();
        System.out.println(getclass(list));
    }

    public static <T> Class<T> getclass(T obj){
        return (Class<T>) obj.getClass();
    }
}
