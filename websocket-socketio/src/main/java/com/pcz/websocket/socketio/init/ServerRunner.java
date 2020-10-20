package com.pcz.websocket.socketio.init;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author picongzhi
 */
@Component
@Slf4j
public class ServerRunner implements CommandLineRunner {
    @Autowired
    private SocketIOServer server;

    @Override
    public void run(String... args) throws Exception {
        server.start();
        log.info("websocket服务器启动成功");
    }
}
