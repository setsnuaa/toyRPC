package com.pgf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/11 17:22
 * @description:
 */
@AllArgsConstructor
@Getter
public enum SerializationTypeEnum {
    PROTOSTUFF((byte)0x01,"protostuff");

    private final byte code;
    private final String name;

    public static String getName(byte code){
        for(SerializationTypeEnum s:SerializationTypeEnum.values()){
            if(s.code==code){
                return s.name;
            }
        }
        return null;
    }
}
