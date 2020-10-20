package com.pcz.mq.rabbitmq.handler;

import cn.hutool.json.JSONUtil;
import com.pcz.mq.rabbitmq.constants.RabbitConsts;
import com.pcz.mq.rabbitmq.message.MessageStruct;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author picongzhi
 */
@Slf4j
@RabbitListener(queues = RabbitConsts.QUEUE_THREE)
@Component
public class QueueThreeHandler {
    @RabbitHandler
    public void directHandlerManualAck(MessageStruct messageStruct, Message message, Channel channel) {
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("队列3，手动ACK，接收消息：{}", JSONUtil.toJsonStr(messageStruct));
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
