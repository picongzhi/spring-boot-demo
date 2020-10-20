package com.pcz.cache.redis;

import com.pcz.cache.redis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Slf4j
public class RedisTest extends SpringBootCacheRedisApplicationTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Serializable> redisCacheTemplate;

    @Test
    public void get() {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        IntStream.range(0, 1000).forEach(i -> executorService.execute(() -> stringRedisTemplate.opsForValue().increment("count", 1)));

        stringRedisTemplate.opsForValue().set("k1", "v1");
        String k1 = stringRedisTemplate.opsForValue().get("k1");
        log.debug("[k1] = {}", k1);

        String key = "pcz:user:1";
        redisCacheTemplate.opsForValue().set(key, new User(1L, "user1"));
        User user = (User) redisCacheTemplate.opsForValue().get(key);
        log.debug("[user] = {}", user);
    }
}
