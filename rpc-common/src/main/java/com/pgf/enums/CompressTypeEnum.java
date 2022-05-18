package com.pgf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/11 15:44
 * @description:
 */
@AllArgsConstructor
@Getter
public enum CompressTypeEnum {
    NULL((byte) 0x00, "null"),
    GZIP((byte) 0x01, "gzip"),
    SNAPPY((byte) 0x02, "snappy");

    private final byte code;
    private final String name;

    public static String getName(byte code) {
        for (CompressTypeEnum c : CompressTypeEnum.values()) {
            if (c.code == code) {
                return c.name;
            }
        }
        return null;
    }
}
