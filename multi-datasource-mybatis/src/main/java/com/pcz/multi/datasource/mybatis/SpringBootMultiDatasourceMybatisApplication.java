package com.pcz.multi.datasource.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author picongzhi
 */
@SpringBootApplication
@MapperScan(basePackages = "com.pcz.multi.datasource.mybatis.mapper")
public class SpringBootMultiDatasourceMybatisApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMultiDatasourceMybatisApplication.class, args);
    }
}
