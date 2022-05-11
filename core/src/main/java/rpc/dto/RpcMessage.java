package rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class RpcMessage {
    private byte messageType;

    private byte serializeType;

    private byte compressType;

    //用于负载均衡
    private int messageID;

    private Object data;
}
