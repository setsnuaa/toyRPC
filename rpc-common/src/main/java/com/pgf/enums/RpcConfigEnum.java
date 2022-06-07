package com.pgf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pan.gefei
 * @name
 * @date 2022/6/6 16:00
 * @description
 */
@AllArgsConstructor
@Getter
public enum RpcConfigEnum {

    RPC_CONFIG_PATH("rpc.properties"),
    ZK_ADDRESS("rpc.zookeeper.address");

    private final String propertyValue;
}
