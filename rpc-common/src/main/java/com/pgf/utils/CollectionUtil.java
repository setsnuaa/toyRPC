package com.pgf.utils;

import java.util.Collection;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/6 16:38
 * @description
 */
public class CollectionUtil {
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }
}
