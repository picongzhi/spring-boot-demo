package com.pcz.orm.mybatis.plus.activerecord;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pcz.orm.mybatis.plus.SpringBootOrmMybatisPlusApplicationTest;
import com.pcz.orm.mybatis.plus.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

@Slf4j
public class ActiveRecordTest extends SpringBootOrmMybatisPlusApplicationTest {
    @Test
    public void testActiveRecordInsert() {
        Role role = new Role();
        role.setName("VIP");
        Assert.assertTrue(role.insert());
        log.debug("[role] = {}", role);
    }

    @Test
    public void testActiveRecordUpdate() {
        Assert.assertTrue(new Role().setId(1L).setName("管理员-1").updateById());
        Assert.assertTrue(new Role().update(new UpdateWrapper<Role>()
                .lambda().set(Role::getName, "普通用户-1")
                .eq(Role::getId, 2)));
    }

    @Test
    public void testActiveRecordSelect() {
        Assert.assertEquals("管理员", new Role().setId(1L).selectById().getName());
        Role role = new Role().selectOne(new QueryWrapper<Role>()
                .lambda().eq(Role::getId, 2));
        Assert.assertEquals("普通用户", role.getName());
        List<Role> roles = new Role().selectAll();
        Assert.assertTrue(roles.size() > 0);
        log.debug("[roles] = {}", roles);
    }

    @Test
    public void testActiveRecordDelete() {
        Assert.assertTrue(new Role().setId(1L).deleteById());
        Assert.assertTrue(new Role().delete(new QueryWrapper<Role>()
                .lambda().eq(Role::getName, "普通用户")));
    }
}