package com.pgf.loadbalance;

import com.pgf.protocol.dto.RpcRequest;
import com.pgf.utils.CollectionUtil;

import java.util.List;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/6 16:44
 * @description
 */
public abstract class AbstractLoadBalance implements LoadBalance{
    @Override
    public String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest) {
        if(CollectionUtil.isEmpty(serviceUrlList)){
            return null;
        }
        if(serviceUrlList.size()==1){
            // 单个服务器直接返回
            return serviceUrlList.get(0);
        }
        return doSelect(serviceUrlList,rpcRequest);
    }

    protected abstract String doSelect(List<String> serviceUrlList, RpcRequest rpcRequest);
}
