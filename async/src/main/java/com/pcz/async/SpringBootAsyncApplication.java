package com.pcz.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author picongzhi
 */
@EnableAsync
@SpringBootApplication
public class SpringBootAsyncApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAsyncApplication.class, args);
    }
}
