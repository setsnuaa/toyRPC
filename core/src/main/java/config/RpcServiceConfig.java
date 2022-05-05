package config;


import lombok.*;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/4/27 20:52
 * @description:rpc服务配置，所有远程服务继承此类
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcServiceConfig {
    /**
     * service version
     */
    private String version = "";
    /**
     * 用来区分同一接口的不同实现
     */
    private String group = "";

    /**
     * rpc service
     */
    private Object service;

    public String getServiceName() {
        return this.service.getClass().getCanonicalName() + " group:" + this.group + " version:" + this.version;
    }
}
