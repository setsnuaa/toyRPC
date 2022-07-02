package com.pgf.registry.zk;

import com.pgf.enums.RpcErrorMessageEnum;
import com.pgf.exception.RpcException;
import com.pgf.extension.ExtensionLoader;
import com.pgf.loadbalance.LoadBalance;
import com.pgf.protocol.dto.RpcRequest;
import com.pgf.provider.impl.ZkServiceProviderImpl;
import com.pgf.registry.ServiceDiscovery;
import com.pgf.registry.zk.util.CuratorUtils;
import com.pgf.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/6 16:26
 * @description
 */
@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {
    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl() {
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("loadBalance");
    }

    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        // like [192.189.1.1:8088,192.168.1.3:9900]
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (CollectionUtil.isEmpty(serviceUrlList)) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        // 负载均衡
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcRequest);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.valueOf(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
