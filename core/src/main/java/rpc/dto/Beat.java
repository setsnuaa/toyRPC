package rpc.dto;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/11 16:12
 * @description:
 */
public final class Beat {
    public static final int BEAT_INTERVAL = 30;
    public static final int BEAT_TIMEOUT=3*BEAT_INTERVAL;
    public static final String BEAT_ID="BEAT_PING_PONG";

    public static RpcRequest BEAT_PING;

    static {
        BEAT_PING=new RpcRequest();
        BEAT_PING.setRequestId(BEAT_ID);
    }
}
