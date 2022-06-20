package com.pgf.compress;

import com.pgf.extension.SPI;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/11 16:21
 * @description:
 */
@SPI
public interface Compress {
    byte[] compress(byte[] bytes);

    byte[] decompress(byte[] bytes);
}
