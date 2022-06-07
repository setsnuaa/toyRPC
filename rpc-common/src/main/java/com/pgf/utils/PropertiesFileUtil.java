package com.pgf.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/6 15:47
 * @description
 */
@Slf4j
public class PropertiesFileUtil {
    private PropertiesFileUtil(){}

    public static Properties readPropertiesFile(String fileName){
        // 拿到当前线程执行的那个类的绝对路径，调用谁的方法拿到谁的绝对路径（不包括类本身）
        URL url=Thread.currentThread().getContextClassLoader().getResource("");
        String rpcConfigPath="";
        if(url!=null){
            rpcConfigPath=url.getPath()+fileName;
        }
        Properties properties=null;
        try(InputStreamReader inputStreamReader=new InputStreamReader(
                new FileInputStream(rpcConfigPath), StandardCharsets.UTF_8
        )){
            properties=new Properties();
            properties.load(inputStreamReader);
        }catch (IOException e){
            log.error("occur exception when read properties file [{}]", fileName);
        }
        return properties;
    }
}
