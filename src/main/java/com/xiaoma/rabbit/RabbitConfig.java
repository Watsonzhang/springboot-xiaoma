package com.xiaoma.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.Executors;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/20 下午1:53
 */
@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;



    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Primary
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setExecutor(Executors.newFixedThreadPool(8));
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        // 开启手动 ack
        factory.setPublisherConfirms(true);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        // 开启回调
        template.setMandatory(true);
        // 用 json 的方式处理消息
        template.setMessageConverter(jsonMessageConverter());
        //        消息确认  yml 需要配置   publisher-returns: true
        template.setConfirmCallback((correlationData, ack, cause) ->
        {
            if (ack) {
                System.out.println("消息发送到exchange成功"+correlationData);
            } else {
                System.out.println("消息发送到exchange失败"+correlationData);
            }
        });
        return template;
    }

    @Bean
    public Queue TestDirectQueue() {
        return new Queue("TestDirectQueue",true);
    }



}
