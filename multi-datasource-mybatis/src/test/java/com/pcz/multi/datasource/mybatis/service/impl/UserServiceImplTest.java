package com.pcz.multi.datasource.mybatis.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pcz.multi.datasource.mybatis.SpringBootMultiDatasourceMybatisApplicationTest;
import com.pcz.multi.datasource.mybatis.model.User;
import com.pcz.multi.datasource.mybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class UserServiceImplTest extends SpringBootMultiDatasourceMybatisApplicationTest {
    @Autowired
    private UserService userService;

    @Test
    public void addUser() {
        User userMaster = User.builder().name("主库添加").age(20).build();
        userService.addUser(userMaster);

        User userSlave = User.builder().name("从库添加").age(20).build();
        userService.save(userSlave);
    }

    @Test
    public void testListUser() {
        List<User> list = userService.list(new QueryWrapper<>());
        log.info("[list] = {}", JSONUtil.toJsonStr(list));
    }
}
