package com.pcz.orm.jpa.repository;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.pcz.orm.jpa.SpringBootOrmJpaApplicationTest;
import com.pcz.orm.jpa.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class UserDaoTest extends SpringBootOrmJpaApplicationTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void testSave() {
        String salt = IdUtil.fastSimpleUUID();
        User testSave3 = User.builder()
                .name("testSave3")
                .password(SecureUtil.md5("123456" + salt))
                .salt(salt)
                .email("testSave3@gmail.com").phoneNumber("12345678910")
                .status(1)
                .lastLoginTime(new DateTime())
                .build();
        userDao.save(testSave3);
        Assert.assertNotNull(testSave3.getId());

        Optional<User> byId = userDao.findById(testSave3.getId());
        Assert.assertTrue(byId.isPresent());
        log.debug("[byId] = {}", byId.get());
    }

    @Test
    public void testDelete() {
        long count = userDao.count();
        userDao.deleteById(1L);
        long left = userDao.count();

        Assert.assertEquals(count - 1, left);
    }

    @Test
    public void testUpdate() {
        userDao.findById(1L).ifPresent(user -> {
            user.setName("name");
            userDao.save(user);
        });

        Assert.assertEquals("name", userDao.findById(1L).get().getName());
    }

    @Test
    public void testQueryOne() {
        Optional<User> byId = userDao.findById(1L);
        Assert.assertTrue(byId.isPresent());
        log.debug("[byId] = {}", byId.get());
    }

    @Test
    public void testQueryAll() {
        List<User> users = userDao.findAll();
        Assert.assertNotEquals(0, users.size());
        log.debug("[users] = {}", users);
    }

    @Test
    public void testQueryPage() {
        initData();

        Integer currentPage = 0;
        Integer pageSize = 5;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sort);
        Page<User> userPage = userDao.findAll(pageRequest);

        Assert.assertEquals(5, userPage.getSize());
        Assert.assertEquals(userDao.count(), userPage.getTotalElements());
        log.debug("[id] = {}", userPage.getContent().stream().map(User::getId).collect(Collectors.toList()));
    }

    private void initData() {
        List<User> users = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            String salt = IdUtil.fastSimpleUUID();
            int index = 3 + i;
            User user = User.builder()
                    .name("testSave" + index)
                    .password(SecureUtil.md5("123456" + salt))
                    .salt(salt)
                    .email("testSave" + index + "@gmail.com").phoneNumber("123456789" + index)
                    .status(1)
                    .lastLoginTime(new DateTime())
                    .build();
            users.add(user);
        }
        userDao.saveAll(users);
    }
}
