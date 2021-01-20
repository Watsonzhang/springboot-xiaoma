package com.xiaoma.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/20 下午6:53
 */


//@Component
@RabbitListener(queues = "TestDirectQueue",executor ="rabbitExec" )//监听的队列名称 TestDirectQueu
public class Consumer {
    @RabbitHandler()
    public void process(Map testMessage) {
        System.out.println( Thread.currentThread().getName());
        System.out.println( Thread.currentThread().getId());
        System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
    }
}
