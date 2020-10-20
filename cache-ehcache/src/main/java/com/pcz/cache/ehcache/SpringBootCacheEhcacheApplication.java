package com.pcz.cache.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author picongzhi
 */
@SpringBootApplication
@EnableCaching
public class SpringBootCacheEhcacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootCacheEhcacheApplication.class, args);
    }
}
