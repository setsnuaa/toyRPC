package com.pgf.protocol.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/4/28 19:56
 * @description:rpc请求
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcRequest implements Serializable {
    //serialVersionUID用来判断序列化前和反序列化后的两个类是否相同
    private static final long serialVersionUID = 1L;
    private String requestId;
    private String interfaceName;
    private String methodName;
    private Object[] params;
    private Class<?>[] paramTypes;
    private String version;
    private String group;

    public String getRpcServiceName() {
        return this.getInterfaceName() + this.getGroup() + this.getVersion();
    }
}
