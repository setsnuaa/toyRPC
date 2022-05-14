package rpc.dto;


import enums.RpcResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/4/28 20:09
 * @description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RpcResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String requestId;
    /**
     * 响应码，表明响应是否成功
     */
    private Integer code;
    private String message;
    /**
     * response body
     */
    private T data;

    public static RpcResponse success(Object data, String requestId) {
        RpcResponse response = new RpcResponse();
        response.setCode(RpcResponseCodeEnum.SUCCESS.getCode());
        response.setMessage(RpcResponseCodeEnum.SUCCESS.getMessage());
        response.setRequestId(requestId);
        if (data != null) {
            response.setData(data);
        }
        return response;
    }

    public static RpcResponse fail() {
        RpcResponse response = new RpcResponse();
        response.setCode(RpcResponseCodeEnum.FAIL.getCode());
        response.setMessage(RpcResponseCodeEnum.FAIL.getMessage());
        return response;
    }
}
