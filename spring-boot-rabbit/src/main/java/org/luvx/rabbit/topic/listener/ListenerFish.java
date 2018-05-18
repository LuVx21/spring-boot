package org.luvx.rabbit.topic.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听两个队列
 */
@Component
@RabbitListener(queues = {"fish"})
public class ListenerFish {

    @RabbitHandler
    public void process(String message) {
        System.out.println("From fish -> 接收消息内容:" + message);
    }
}


