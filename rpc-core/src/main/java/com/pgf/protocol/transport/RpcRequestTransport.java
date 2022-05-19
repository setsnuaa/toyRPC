package com.pgf.protocol.transport;

import com.pgf.extension.SPI;
import com.pgf.protocol.dto.RpcRequest;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/19 15:47
 * @description:客户端发送rpc请求并得到相应
 */
@SPI
public interface RpcRequestTransport {
    /**
     * @param rpcRequest:rpcMessage的body部分
     * @return 服务端相应
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
