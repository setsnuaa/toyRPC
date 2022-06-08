package com.pgf.compress.snappy;

import com.pgf.compress.Compress;

import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/18 17:04
 * @description:snappy压缩、解压速度非常快
 */
public class SnappyCompress implements Compress {

    private static final int BUFFER_SIZE = 1024 * 4;

    @Override
    public byte[] compress(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("bytes is null");
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             SnappyOutputStream snappy = new SnappyOutputStream(out)) {
            snappy.write(bytes);
            snappy.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("snappy compress error", e);
        }
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("bytes is null");
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             SnappyInputStream snappy = new SnappyInputStream(new ByteArrayInputStream(bytes))) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int n;
            while ((n = snappy.read(buffer)) > -1) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("snappy decompress error", e);
        }
    }
}
