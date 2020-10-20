package com.pcz.rbac.shiro;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author picongzhi
 */
@SpringBootApplication
@MapperScan("com.pcz.rbac.shiro.mapper")
public class SpringBootRbacShiroApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRbacShiroApplication.class, args);
    }
}
