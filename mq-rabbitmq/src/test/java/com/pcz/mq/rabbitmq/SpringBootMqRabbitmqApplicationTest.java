package com.pcz.mq.rabbitmq;

import cn.hutool.core.date.DateUtil;
import com.pcz.mq.rabbitmq.constants.RabbitConsts;
import com.pcz.mq.rabbitmq.message.MessageStruct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMqRabbitmqApplicationTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 直接模式
     */
    @Test
    public void sendDirect() {
        rabbitTemplate.convertAndSend(RabbitConsts.DIRECT_MODE_QUEUE_ONE,
                new MessageStruct("direct message"));
    }

    /**
     * 分列模式发送
     */
    @Test
    public void sendFanout() {
        rabbitTemplate.convertAndSend(RabbitConsts.FANOUT_MODE_QUEUE, "",
                new MessageStruct("fanout message"));
    }

    /**
     * 主题模式发送
     */
    @Test
    public void sendTopic1() {
        rabbitTemplate.convertAndSend(RabbitConsts.TOPIC_MODE_QUEUE, "queue.aaa.bbb",
                new MessageStruct("topic message"));
    }

    @Test
    public void sendTopic2() {
        rabbitTemplate.convertAndSend(RabbitConsts.TOPIC_MODE_QUEUE, "ccc.queue",
                new MessageStruct("topic message"));
    }

    @Test
    public void sendTopic3() {
        rabbitTemplate.convertAndSend(RabbitConsts.TOPIC_MODE_QUEUE, "3.queue",
                new MessageStruct("topic message"));
    }

    @Test
    public void sendDelay() {
        rabbitTemplate.convertAndSend(RabbitConsts.DELAY_MODE_QUEUE, RabbitConsts.DELAY_QUEUE,
                new MessageStruct("delay message, delay 5s, " + DateUtil.date()),
                message -> {
                    message.getMessageProperties().setHeader("x-delay", 5000);
                    return message;
                });
        rabbitTemplate.convertAndSend(RabbitConsts.DELAY_MODE_QUEUE, RabbitConsts.DELAY_QUEUE,
                new MessageStruct("delay message, delay 2s, " + DateUtil.date()),
                message -> {
                    message.getMessageProperties().setHeader("x-delay", 2000);
                    return message;
                });
        rabbitTemplate.convertAndSend(RabbitConsts.DELAY_MODE_QUEUE, RabbitConsts.DELAY_QUEUE,
                new MessageStruct("delay message, delay 8s, " + DateUtil.date()),
                message -> {
                    message.getMessageProperties().setHeader("x-delay", 8000);
                    return message;
                });
    }
}
