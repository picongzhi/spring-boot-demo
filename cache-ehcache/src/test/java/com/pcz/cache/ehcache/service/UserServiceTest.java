package com.pcz.cache.ehcache.service;

import com.pcz.cache.ehcache.SpringBootCacheEhcacheApplicationTest;
import com.pcz.cache.ehcache.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserServiceTest extends SpringBootCacheEhcacheApplicationTest {
    @Autowired
    private UserService userService;

    @Test
    public void getTwice() {
        User user1 = userService.get(1L);
        log.debug("[user1] = {}", user1);

        User user2 = userService.get(1L);
        log.debug("[user2] = {}", user2);
    }

    @Test
    public void getAfterSave() {
        userService.saveOrUpdate(new User(4L, "user4"));

        User user = userService.get(4L);
        log.debug("[user] = {}", user);
    }

    @Test
    public void deleteUser() {
        userService.get(1L);
        userService.delete(1L);
    }
}
