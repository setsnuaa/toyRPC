package compress;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/11 16:21
 * @description:
 */
public interface Compress {
    byte[] compress(byte[] bytes);

    byte[] decompress(byte[] bytes);
}
