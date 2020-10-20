package com.pcz.sharding.jdbc;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pcz.sharding.jdbc.mapper.OrderMapper;
import com.pcz.sharding.jdbc.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootShardingJdbcApplicationTest {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testInsert() {
        for (long i = 1; i < 10; i++) {
            for (long j = 1; j < 20; j++) {
                Order order = Order.builder()
                        .userId(i)
                        .orderId(j)
                        .remark(RandomUtil.randomString(20))
                        .build();
                orderMapper.insert(order);
            }
        }
    }

    @Test
    public void testUpdate() {
        Order update = new Order();
        update.setRemark("修改备注");
        orderMapper.update(update,
                Wrappers.<Order>update()
                        .lambda()
                        .eq(Order::getOrderId, 2)
                        .eq(Order::getUserId, 2));
    }

    @Test
    public void testDelete() {
        orderMapper.delete(new QueryWrapper<>());
    }

    @Test
    public void testSelect() {
        List<Order> orders = orderMapper.selectList(Wrappers.<Order>query()
                .lambda()
                .in(Order::getOrderId, 1, 2));
        log.info("[orders] = {}", JSONUtil.toJsonStr(orders));
    }
}
