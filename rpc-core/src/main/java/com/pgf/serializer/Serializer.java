package com.pgf.serializer;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/5 19:28
 * @description:
 */
public interface Serializer {
    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
