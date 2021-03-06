package com.pgf.serializer.protobuf;

import com.pgf.exception.SerializeException;
import com.pgf.serializer.Serializer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/5 19:33
 * @description:用Protobuf作为序列化反序列化器，protostuff是protobuf的java实现版本
 */
public class ProtobufSerializer implements Serializer {
    //避免每次序列化重新申请buffer
    private static final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

    //将使用过的schema缓存起来，避免重复实例化同样的schema
    //schema作用：序列化和反序列化的映射关系
    private Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    private <T> Schema<T> getSchema(Class<T> cls) {
        return (Schema<T>) cachedSchema.computeIfAbsent(cls, key -> RuntimeSchema.getSchema(key));
    }

    public byte[] serialize(Object obj) {
        Class<?> clazz = obj.getClass();
        Schema schema = getSchema(clazz);
        byte[] bytes;
        try {
            bytes = ProtobufIOUtil.toByteArray(obj, schema, BUFFER);
        } catch (Exception e) {
            throw new SerializeException(e.getMessage());
        } finally {
            BUFFER.clear();
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try {
            Schema<T> schema = getSchema(clazz);
            T obj = schema.newMessage();
            ProtobufIOUtil.mergeFrom(bytes, obj, schema);
            return obj;
        } catch (Exception e) {
            throw new SerializeException(e.getMessage());
        }
    }
}
