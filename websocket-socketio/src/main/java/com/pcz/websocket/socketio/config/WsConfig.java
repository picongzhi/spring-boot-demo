package com.pcz.websocket.socketio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author picongzhi
 */
@ConfigurationProperties(prefix = "ws.server")
@Data
public class WsConfig {
    /**
     * 端口
     */
    private Integer port;

    /**
     * 主机
     */
    private String host;
}
