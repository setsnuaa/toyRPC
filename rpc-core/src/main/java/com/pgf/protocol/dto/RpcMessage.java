package com.pgf.protocol.dto;

import lombok.*;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/11 17:00
 * @description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RpcMessage {
    private byte messageType;

    private byte serializeType;

    private byte compressType;

    //用于负载均衡
    private int messageID;

    private Object data;
}
