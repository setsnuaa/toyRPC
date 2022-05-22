package com.pgf.util;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/21 17:00
 * @description:
 */
public class RuntimeUtil {
    /**
     * 获取cpu核心数
     */
    public static int cpus(){
        return Runtime.getRuntime().availableProcessors();
    }
}
