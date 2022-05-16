package rpc.codec;

import constants.RpcConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import serializer.Serializer;

import java.util.List;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/10 15:07
 * @description:LengthFieldBasedFrameDecoder用来解决TCP拆包、粘包问题 <rpcMessage>
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
public class RpcDecoder extends LengthFieldBasedFrameDecoder {
    public RpcDecoder() {
        //full length在第一个字节，所以lengthFiledOffset=0
        //full length占四个字节，所以lengthFieldLength=4
        //已经读了4个字节，所以lengthAdjustment=-4
        //读取head+body，所以initialBytesToStrip=0
        this(RpcConstants.MAX_FRAME_LENGTH, 0, 4, -4, 0);
    }

    /**
     * @param maxFrameLength      超过最大长度会被抛弃
     * @param lengthFieldOffset   读取字节的起始地址
     * @param lengthFieldLength   读取某个长度需要的字节数
     * @param lengthAdjustment    这个数加上之前的某个长度就是这次需要的数据的结尾
     * @param initialBytesToStrip 需要的数据的开头，比如需要head+body，那设置为0，只需要body，设置为head的长度
     */
    public RpcDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
                      int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decoded = super.decode(ctx, in);
        if (decoded instanceof ByteBuf) {
            ByteBuf message = (ByteBuf) decoded;
            if (message.readableBytes() >= RpcConstants.HEAD_LENGTH) {
                try {
                    return decodeMessage(message);
                } catch (Exception e) {
                    log.error("Decode RPC message error", e);
                    throw e;
                } finally {
                    message.release();
                }
            }
        }
        return decoded;
    }

    private Object decodeMessage(ByteBuf in) {
        return null;
    }

    private void checkVersion(ByteBuf in) {
        byte version = in.readByte();
        if (version != RpcConstants.Protocol_VERSION) {
            throw new RuntimeException("version isn't compatible:" + version);
        }
    }
}
