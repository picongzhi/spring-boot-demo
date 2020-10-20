package com.pcz.orm.mybatis.plus.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pcz.orm.mybatis.plus.SpringBootOrmMybatisPlusApplicationTest;
import com.pcz.orm.mybatis.plus.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class UserServiceTest extends SpringBootOrmMybatisPlusApplicationTest {
    @Autowired
    private UserService userService;

    @Test
    public void testSave() {
        String salt = IdUtil.fastSimpleUUID();
        User testSave3 = User.builder()
                .name("testSave3")
                .password(SecureUtil.md5("123456" + salt))
                .salt(salt)
                .email("testSave3@gmail.com")
                .phoneNumber("12345678910")
                .status(1)
                .lastLoginTime(new DateTime())
                .build();
        boolean save = userService.save(testSave3);
        Assert.assertTrue(save);
        log.debug("[测试id回显 testSave3.getId()] = {}", testSave3.getId());
    }

    @Test
    public void testSaveList() {
        List<User> userList = Lists.newArrayList();
        for (int i = 4; i < 14; i++) {
            String salt = IdUtil.fastSimpleUUID();
            User user = User.builder()
                    .name("testSave" + i)
                    .password(SecureUtil.md5("12345" + salt))
                    .salt(salt)
                    .email("testSave" + i + "@gmail.com")
                    .phoneNumber("123456789" + i)
                    .status(1)
                    .lastLoginTime(new DateTime())
                    .build();
            userList.add(user);
        }
        boolean batch = userService.saveBatch(userList);
        Assert.assertTrue(batch);
        List<Long> ids = userList.stream().map(User::getId).collect(Collectors.toList());
        log.debug("[userList ids] = {}", ids);
    }

    @Test
    public void testDelete() {
        boolean remove = userService.removeById(1L);
        Assert.assertTrue(remove);
        User byId = userService.getById(1L);
        Assert.assertNull(byId);
    }

    @Test
    public void testUpdate() {
        User user = userService.getById(1L);
        Assert.assertNotNull(user);

        user.setName("name");
        boolean b = userService.updateById(user);
        Assert.assertTrue(b);

        User update = userService.getById(1L);
        Assert.assertEquals("name", update.getName());
        log.debug("[update] = {}", update);
    }

    @Test
    public void testQueryOne() {
        User user = userService.getById(1L);
        Assert.assertNotNull(user);
        log.debug("[user] = {}", user);
    }

    @Test
    public void testQueryAll() {
        List<User> userList = userService.list(new QueryWrapper<>());
        Assert.assertTrue(CollUtil.isNotEmpty(userList));
        log.debug("[list] = {}", userList);
    }

    @Test
    public void testQueryByPageAndSort() {
        initData();
        int count = userService.count(new QueryWrapper<>());
        Page<User> userPage = new Page<>(1, 5);
        userPage.setDesc("id");

        IPage<User> page = userService.page(userPage, new QueryWrapper<>());
        Assert.assertEquals(5, page.getSize());
        Assert.assertEquals(count, page.getTotal());
        log.debug("[page.getRecords()] = {}", page.getRecords());
    }

    @Test
    public void testQueryByCondition() {
        initData();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", "Save1").or()
                .eq("phone_number", "12345678910")
                .orderByDesc("id");
        int count = userService.count(wrapper);
        Page<User> userPage = new Page<>(1, 3);
        IPage<User> page = userService.page(userPage, wrapper);
        Assert.assertEquals(3, page.getSize());
        Assert.assertEquals(count, page.getTotal());
        log.debug("[page.getRecords() = {}]", page.getRecords());
    }

    private void initData() {
        testSaveList();
    }
}
