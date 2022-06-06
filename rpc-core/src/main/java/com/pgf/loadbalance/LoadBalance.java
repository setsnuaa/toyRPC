package com.pgf.loadbalance;

import com.pgf.extension.SPI;
import com.pgf.protocol.dto.RpcRequest;

import java.util.List;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/6 16:27
 * @description 一个服务可能有多台服务器提供，选择其中一台服务器
 */
@SPI
public interface LoadBalance {
    String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest);
}
