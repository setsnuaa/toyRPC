package com.pgf.serializer;

import com.pgf.extension.SPI;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/5 19:28
 * @description:
 */
@SPI
public interface Serializer {
    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
