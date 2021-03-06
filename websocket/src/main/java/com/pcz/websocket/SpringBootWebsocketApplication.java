package com.pcz.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author picongzhi
 */
@SpringBootApplication
@EnableScheduling
public class SpringBootWebsocketApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebsocketApplication.class, args);
    }
}
