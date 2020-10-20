package com.pcz.mq.rabbitmq.config;

import com.google.common.collect.Maps;
import com.pcz.mq.rabbitmq.constants.RabbitConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author picongzhi
 */
@Slf4j
@Configuration
public class RabbitMqConfig {
    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationDate, ack, cause) ->
                log.info("消息发送成功:correlationData({}),ack({}),cause({})",
                        correlationDate, ack, cause));
        rabbitTemplate.setReturnCallback(((message, replyCode, replyText, exchange, routingKey) ->
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",
                        exchange, routingKey, replyCode, replyText, message)));

        return rabbitTemplate;
    }

    /**
     * 直接模式队列1
     *
     * @return
     */
    @Bean
    public Queue directOneQueue() {
        return new Queue(RabbitConsts.DIRECT_MODE_QUEUE_ONE);
    }

    /**
     * 队列2
     *
     * @return
     */
    @Bean
    public Queue queueTwo() {
        return new Queue(RabbitConsts.QUEUE_TWO);
    }

    /**
     * 队列3
     *
     * @return
     */
    @Bean
    public Queue queueThree() {
        return new Queue(RabbitConsts.QUEUE_THREE);
    }

    /**
     * 分列模式队列
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitConsts.FANOUT_MODE_QUEUE);
    }

    /**
     * 分列模式绑定队列1
     *
     * @param directOneQueue
     * @param fanoutExchange
     * @return
     */
    @Bean
    public Binding fanoutBinding1(Queue directOneQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(directOneQueue).to(fanoutExchange);
    }

    /**
     * 分列模式绑定队列2
     *
     * @param queueTwo
     * @param fanoutExchange
     * @return
     */
    @Bean
    public Binding fanoutBinding2(Queue queueTwo, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueTwo).to(fanoutExchange);
    }

    /**
     * 主题模式队列
     * 路由格式必须以.分隔，比如user.email或者user.aaa.email
     * 通配符*代表一个占位符，或者一个单词，比如路由为user.*，那么user.email可以匹配，但是user.aaa.email就不可以
     * 通配符#代表一个过多个占位符，或者一个或多个单词，比如路由为user.#，那么user.email可以匹配，user.aaa.email也可以匹配
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitConsts.TOPIC_MODE_QUEUE);
    }

    /**
     * 主题模式绑定分列模式
     *
     * @param fanoutExchange
     * @param topicExchange
     * @return
     */
    @Bean
    public Binding topicBinding1(FanoutExchange fanoutExchange, TopicExchange topicExchange) {
        return BindingBuilder.bind(fanoutExchange).to(topicExchange).with(RabbitConsts.TOPIC_ROUTING_KEY_ONE);
    }

    /**
     * 主题模式绑定队列2
     *
     * @param queueTwo
     * @param topicExchange
     * @return
     */
    @Bean
    public Binding topicBinding2(Queue queueTwo, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueTwo).to(topicExchange).with(RabbitConsts.TOPIC_ROUTING_KEY_TWO);
    }

    /**
     * 主题模式绑定队列3
     *
     * @param queueThree
     * @param topicExchange
     * @return
     */
    @Bean
    public Binding topicBinding3(Queue queueThree, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueThree).to(topicExchange).with(RabbitConsts.TOPIC_ROUTING_KEY_THREE);
    }

    /**
     * 延迟队列
     *
     * @return
     */
    @Bean
    public Queue delayQueue() {
        return new Queue(RabbitConsts.DELAY_QUEUE, true);
    }

    /**
     * 延迟队列交换器，x-delayed-type和x-delayed-message固定
     *
     * @return
     */
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = Maps.newHashMap();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(RabbitConsts.DELAY_MODE_QUEUE, "x-delayed-message", true, false, args);
    }

    /**
     * 延迟队列绑定自定义交换器
     *
     * @param delayQueue
     * @param delayExchange
     * @return
     */
    @Bean
    public Binding delayBinding(Queue delayQueue, CustomExchange delayExchange) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(RabbitConsts.DELAY_QUEUE).noargs();
    }
}
