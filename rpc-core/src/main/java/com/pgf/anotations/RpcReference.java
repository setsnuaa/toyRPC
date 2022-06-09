package com.pgf.anotations;

import java.lang.annotation.*;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/9 16:01
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {
    String version() default "";

    String group() default "";
}
