package com.pcz.orm.mybatis.mapper.page.mapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcz.orm.mybatis.mapper.page.SpringBootOrmMybatisMapperPageApplicationTest;
import com.pcz.orm.mybatis.mapper.page.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class UserMapperTest extends SpringBootOrmMybatisMapperPageApplicationTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        String salt = IdUtil.fastSimpleUUID();
        User testSave3 = User.builder()
                .name("testSave3")
                .password(SecureUtil.md5("123456" + salt))
                .salt(salt)
                .email("testSave3@gmail.com")
                .phoneNumber("12345678910")
                .status(1)
                .lastLoginTime(new DateTime())
                .createTime(new DateTime())
                .lastUpdateTime(new DateTime())
                .build();
        userMapper.insertUseGeneratedKeys(testSave3);
        Assert.assertNotNull(testSave3.getId());
        log.debug("[测试主键回写 testSave3.getId()] = {}", testSave3.getId());
    }

    @Test
    public void testInsertList() {
        List<User> userList = Lists.newArrayList();
        for (int i = 4; i < 14; i++) {
            String salt = IdUtil.fastSimpleUUID();
            User user = User.builder()
                    .name("testSave" + i)
                    .password(SecureUtil.md5("123456" + salt))
                    .salt(salt)
                    .email("testSave" + i + "@gmail.com")
                    .phoneNumber("123456789" + i)
                    .status(1)
                    .lastLoginTime(new DateTime())
                    .createTime(new DateTime())
                    .lastUpdateTime(new DateTime())
                    .build();
            userList.add(user);
        }

        int i = userMapper.insertList(userList);
        Assert.assertEquals(userList.size(), i);
        List<Long> ids = userList.stream().map(User::getId).collect(Collectors.toList());
        log.debug("[测试主键回写 userList.ids] = {}", ids);
    }

    @Test
    public void testDelete() {
        Long primaryKey = 1L;
        int i = userMapper.deleteByPrimaryKey(primaryKey);
        Assert.assertEquals(1, i);

        User user = userMapper.selectByPrimaryKey(primaryKey);
        Assert.assertNull(user);
    }

    @Test
    public void testUpdate() {
        Long primaryKey = 1L;
        User user = userMapper.selectByPrimaryKey(primaryKey);
        user.setName("name");
        int i = userMapper.updateByPrimaryKeySelective(user);
        Assert.assertEquals(1, i);

        User update = userMapper.selectByPrimaryKey(primaryKey);
        Assert.assertNotNull(update);
        Assert.assertEquals("name", update.getName());
        log.debug("[update] = {}", update);
    }

    @Test
    public void testQueryOne() {
        User user = userMapper.selectByPrimaryKey(1L);
        Assert.assertNotNull(user);
        log.debug("[user] = {}", user);
    }

    @Test
    public void testQueryAll() {
        List<User> userList = userMapper.selectAll();
        Assert.assertTrue(CollUtil.isNotEmpty(userList));
        log.debug("[users] = {}", userList);
    }

    @Test
    public void testQueryByPageAndSort() {
        initDate();
        int currentPage = 1;
        int pageSize = 5;
        String orderBy = "id desc";
        int count = userMapper.selectCount(null);
        PageHelper.startPage(currentPage, pageSize, orderBy);

        List<User> userList = userMapper.selectAll();
        PageInfo<User> userPageInfo = new PageInfo<>(userList);

        Assert.assertEquals(5, userPageInfo.getSize());
        Assert.assertEquals(count, userPageInfo.getTotal());
        log.debug("[userPageInfo] = {}", userPageInfo);
    }

    @Test
    public void testQueryByCondition() {
        initDate();
        Example example = new Example(User.class);
        example.createCriteria()
                .andLike("name", "%Save1%")
                .orEqualTo("phoneNumber", "1234567891");
        example.setOrderByClause("id desc");

        int count = userMapper.selectCountByExample(example);
        PageHelper.startPage(1, 3);
        List<User> userList = userMapper.selectByExample(example);
        PageInfo<User> userPageInfo = new PageInfo<>(userList);

        Assert.assertEquals(3, userPageInfo.getSize());
        Assert.assertEquals(count, userPageInfo.getTotal());
        log.debug("[userPageInfo] = {}", userPageInfo);
    }

    private void initDate() {
        testInsertList();
    }
}
