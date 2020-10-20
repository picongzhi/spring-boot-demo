package com.pcz.sharding.jdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author picongzhi
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("com.pcz.sharding.jdbc.mapper")
public class SpringBootShardingJdbcApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootShardingJdbcApplication.class, args);
    }
}
