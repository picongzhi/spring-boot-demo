package com.pcz.orm.mybatis.plus.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author picongzhi
 */
@Configuration
@MapperScan(basePackages = {"com.pcz.orm.mybatis.plus.mapper"})
@EnableTransactionManagement
public class MybatisPlusConfig {
    /**
     * 性能分析拦截器
     *
     * @return PerformanceInterceptor
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * 分页插件
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
