package com.pcz.orm.mybatis.mapper.page;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author picongzhi
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.pcz.orm.mybatis.mapper.page.mapper"})
public class SpringBootOrmMybatisMapperPageApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootOrmMybatisMapperPageApplication.class, args);
    }
}
