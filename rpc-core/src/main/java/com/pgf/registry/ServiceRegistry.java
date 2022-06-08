package com.pgf.registry;

import com.pgf.extension.SPI;

import java.net.InetSocketAddress;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/19 16:55
 * @description:
 */
@SPI
public interface ServiceRegistry {
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
