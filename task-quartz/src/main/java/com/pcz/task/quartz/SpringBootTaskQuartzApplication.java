package com.pcz.task.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author picongzhi
 */
@MapperScan(basePackages = {"com.pcz.task.quartz.mapper"})
@SpringBootApplication
public class SpringBootTaskQuartzApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootTaskQuartzApplication.class, args);
    }
}
