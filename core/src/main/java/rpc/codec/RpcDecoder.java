package rpc.codec;

import rpc.dto.RpcConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import serializer.Serializer;

import java.util.List;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/10 15:07
 * @description:
 */
@Slf4j
@AllArgsConstructor
public class RpcDecoder extends ByteToMessageDecoder {
    private Class<?> genericClass;
    private Serializer serializer;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes()< RpcConstants.HEAD_LENGTH){

        }
    }
}
