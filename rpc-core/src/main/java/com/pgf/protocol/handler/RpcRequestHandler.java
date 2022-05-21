package com.pgf.protocol.handler;

import com.pgf.exception.RpcException;
import com.pgf.factory.SingletonFactory;
import com.pgf.protocol.dto.RpcRequest;
import com.pgf.provider.ServiceProvider;
import com.pgf.provider.impl.ZkServiceProviderImpl;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/21 15:38
 * @description: 处理rpc请求
 */
@Slf4j
public class RpcRequestHandler {
    private ServiceProvider serviceProvider;

    public RpcRequestHandler() {
        serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    public Object handle(RpcRequest rpcRequest) {
        Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
        return invokeTargetMethod(rpcRequest, service);
    }

    /**
     *
     * @param rpcRequest 客户端请求
     * @param service 服务端提供的服务
     * @return 方法调用结果
     */
    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) {
        Object result;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            result = method.invoke(service, rpcRequest.getParams());
            log.info("service:[{}] successfully invoke method:[{}]", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RpcException(e.getMessage(), e);
        }
        return result;
    }
}
