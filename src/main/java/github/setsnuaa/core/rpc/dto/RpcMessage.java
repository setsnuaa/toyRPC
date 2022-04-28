package github.setsnuaa.core.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/4/28 19:52
 * @description:消息定义
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcMessage {
    /**
     * 消息类型
     */
    private byte messageType;
    /**
     * 序列化类型
     */
    private byte serialType;

    private byte compressType;

    private int requestId;

    private Object data;
}
