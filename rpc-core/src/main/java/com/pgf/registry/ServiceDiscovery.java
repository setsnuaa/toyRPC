package com.pgf.registry;

import com.pgf.protocol.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/19 16:55
 * @description: 根据服务名称发现服务端
 */
public interface ServiceDiscovery {
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}
