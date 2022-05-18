package com.pgf.protocol.codec;

import com.pgf.compress.Compress;
import com.pgf.constants.RpcConstants;
import com.pgf.enums.CompressTypeEnum;
import com.pgf.enums.SerializationTypeEnum;
import com.pgf.extension.ExtensionLoader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.pgf.protocol.dto.RpcMessage;
import com.pgf.serializer.Serializer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/10 14:54
 * @description:rpcMessage=head+body(request or response)
 * <rpcMessage>
 * 0                  1   2   3   4   5             6           7          8  9  10 11 12
 * +------------------+---+---+---+---+-------------+-----------+----------+--+--+--+--+
 * | protocol version | full length   | messageType | serialize | compress | messageID |
 * +-----------------------+--------+---------------------+-----------+----------------+
 * |                                                                                   |
 * |                                         body                                      |
 * |                                                                                   |
 * |                                                                                   |
 * +-----------------------------------------------------------------------------------+
 * 1B protocol version         4B full length（消息长度）    1B messageType（消息类型）
 * 1B serialize（序列化类型）    1B compress（压缩类型）
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
        try {
            //rpc message head
            int fullLength = RpcConstants.HEAD_LENGTH;
            byteBuf.writeByte(RpcConstants.Protocol_VERSION);
            //留4个字节给full length
            byteBuf.writerIndex(byteBuf.writerIndex() + 4);
            byte messageType = rpcMessage.getMessageType();
            byteBuf.writeByte(messageType);
            byteBuf.writeByte(rpcMessage.getSerializeType());
            byteBuf.writeByte(rpcMessage.getCompressType());
            byteBuf.writeInt(ATOMIC_INTEGER.getAndIncrement());

            //rpc message body
            byte[] body = null;
            if (messageType != RpcConstants.HEARTBEAT_REQUEST_TYPE
                    && messageType != RpcConstants.HEARTBEAT_RESPONSE_TYPE) {
                String serializerName = SerializationTypeEnum.getName(rpcMessage.getSerializeType());
                log.info("serializer name:[{}]", serializerName);
                Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class).getExtension(serializerName);
                body = serializer.serialize(rpcMessage.getData());
                String compressName = CompressTypeEnum.getName(rpcMessage.getCompressType());
                log.info("compress name:[{}]", compressName);
                Compress compress = ExtensionLoader.getExtensionLoader(Compress.class).getExtension(compressName);
                body = compress.compress(body);
                fullLength += body.length;
            }

            if (body != null) {
                byteBuf.writeBytes(body);
            }

            int writeIndex = byteBuf.writerIndex();
            byteBuf.writerIndex(writeIndex - fullLength + 1);
            byteBuf.writeInt(fullLength);
            byteBuf.writerIndex(writeIndex);
        } catch (Exception e) {
            log.error("Encode error", e.getMessage());
        }
    }
}
