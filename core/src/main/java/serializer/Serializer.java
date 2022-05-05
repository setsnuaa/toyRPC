package serializer;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/5 19:28
 * @description:
 */
public interface Serializer {
    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] bytes,Class<T> clazz);
}
