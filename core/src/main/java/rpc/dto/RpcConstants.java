package rpc.dto;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/4/27 21:49
 * @description:rpc报文定义
 */

/**
 * 将消息分为 head和 body，head中包含消息总长
 */
public class RpcConstants {
    /**
     * Magic number
     * 用于确认RpcMessage
     */
    public static final byte[] MAGIC_NUMBER = { (byte) 'r', (byte) 'p', (byte) 'c'};
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    //协议版本信息，如果以后扩展了新的协议，用不同的协议解析器
    public static final byte VERSION = 1;

    public static final byte REQUEST_TYPE = 1;
    public static final byte RESPONSE_TYPE = 2;

    //心跳
    //ping
    public static final byte HEARTBEAT_REQUEST_TYPE = 3;
    //pong
    public static final byte HEARTBEAT_RESPONSE_TYPE = 4;

    public static final int HEAD_LENGTH = 16;
    //消息总长默认16，只有head，没有body
    public static final byte TOTAL_LENGTH = 16;
    public static final int MAX_FRAME_LENGTH = 8 * 1024 * 1024;

    public static final String PING = "ping";
    public static final String PONG = "pong";
}
