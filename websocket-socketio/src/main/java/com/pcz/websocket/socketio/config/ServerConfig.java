package com.pcz.websocket.socketio.config;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author picongzhi
 */
@Configuration
@EnableConfigurationProperties({WsConfig.class})
public class ServerConfig {
    @Bean
    public SocketIOServer server(WsConfig wsConfig) {
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setHostname(wsConfig.getHost());
        configuration.setPort(wsConfig.getPort());
        configuration.setAuthorizationListener(data -> {
            String token = data.getSingleUrlParam("token");
            // 验证token的合法性，实际业务需要校验token是否过期
            return StrUtil.isNotBlank(token);
        });

        return new SocketIOServer(configuration);
    }

    /**
     * Spring扫描自定义注解
     *
     * @param server
     * @return
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer server) {
        return new SpringAnnotationScanner(server);
    }
}
