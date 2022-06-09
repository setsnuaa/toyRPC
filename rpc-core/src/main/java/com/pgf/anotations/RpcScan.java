package com.pgf.anotations;

import com.pgf.spring.CustomScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/9 16:00
 * @description
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegistrar.class)
@Documented
public @interface RpcScan {
    String[] basePackage();
}
