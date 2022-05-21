package com.pgf.provider;

import com.pgf.config.RpcServiceConfig;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/4/28 21:09
 * @description: 提供方法调用的服务器调用这个接口的方法来发布服务
 */
public interface ServiceProvider {

    void addService(RpcServiceConfig rpcServiceConfig);

    Object getService(String rpcServiceName);

    void publishService(RpcServiceConfig rpcServiceConfig);

}
