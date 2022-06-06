package com.pgf.loadbalance.loadbalancer;

import com.pgf.loadbalance.AbstractLoadBalance;
import com.pgf.protocol.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/6 16:47
 * @description
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceUrlList, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceUrlList.get(random.nextInt(serviceUrlList.size()));
    }
}
