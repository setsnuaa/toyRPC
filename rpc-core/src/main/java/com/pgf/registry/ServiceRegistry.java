package com.pgf.registry;

import java.net.InetSocketAddress;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/19 16:55
 * @description:
 */
public interface ServiceRegistry {
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
