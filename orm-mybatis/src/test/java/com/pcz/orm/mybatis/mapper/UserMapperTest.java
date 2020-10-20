package com.pcz.orm.mybatis.mapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.pcz.orm.mybatis.SpringBootOrmMybatisApplicationTest;
import com.pcz.orm.mybatis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class UserMapperTest extends SpringBootOrmMybatisApplicationTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectAllUser() {
        List<User> userList = userMapper.selectAllUser();
        Assert.assertTrue(CollUtil.isNotEmpty(userList));
        log.debug("[userList] = {}", userList);
    }

    @Test
    public void selectUserById() {
        User user = userMapper.selectUserById(1L);
        Assert.assertNotNull(user);
        log.debug("[user] = {}", user);
    }

    @Test
    public void saveUser() {
        String salt = IdUtil.fastSimpleUUID();
        User user = User.builder()
                .name("testSave3")
                .password(SecureUtil.md5("123456" + salt))
                .salt(salt)
                .email("testSave3@gmail.com")
                .phoneNumber("12345678910")
                .status(1)
                .lastLoginTime(new DateTime())
                .createTime(new DateTime())
                .lastUpdateTime(new DateTime()).build();
        int i = userMapper.saveUser(user);
        Assert.assertEquals(1, i);
    }

    @Test
    public void deleteById() {
        int i = userMapper.deleteById(1L);
        Assert.assertEquals(1, i);
    }
}
