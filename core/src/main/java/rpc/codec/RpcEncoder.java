package rpc.codec;

import compress.Compress;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rpc.dto.RpcMessage;
import rpc.dto.RpcRequest;
import rpc.dto.RpcResponse;
import serializer.Serializer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/10 14:54
 * @description:rpcMessage=head+body(request or response)
 * <rpcMessage>
 *   0   1   2   3   4             5           6          7                  8  9  10 11 12
 *   +---+---+---+---+-------------+-----------+----------+------------------+--+--+--+--+
 *   | full length   | messageType | serialize | compress | protocol version | messageID |
 *   +-----------------------+--------+---------------------+-----------+----------------+
 *   |                                                                                   |
 *   |                                         body                                      |
 *   |                                                                                   |
 *   |                                                                                   |
 *   +-----------------------------------------------------------------------------------+
 * 4B full length（消息长度）    1B messageType（消息类型）
 * 1B serialize（序列化类型）    1B compress（压缩类型）    1B protocol version
 * body（object类型数据）
 * </rpcMessage>
 */
@Slf4j
@AllArgsConstructor
public class RpcEncoder extends MessageToByteEncoder<RpcMessage> {
    //自增ID
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage, ByteBuf byteBuf) throws Exception {
        try{

        }catch (Exception e){
            log.error("Encode error",e.getMessage());
        }
    }
}
