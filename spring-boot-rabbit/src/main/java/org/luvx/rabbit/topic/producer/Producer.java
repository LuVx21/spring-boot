package org.luvx.rabbit.topic.producer;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendtorabbit() {
        String message = "hello rabbit!";
        System.out.println("发送消息内容: " + message);
        this.rabbitTemplate.convertAndSend("LuVx-TopicExchange", "rabbit.1", message);
    }

    public void sendtofish() {
        String message = "hello fish!";
        System.out.println("发送消息内容: " + message);
        this.rabbitTemplate.convertAndSend("LuVx-TopicExchange", "2.fish", message);
    }

    /**
     * 符合多个路由key的消息,
     * 这涉及到队列的优先级问题
     */
    public void sendall() {
        String message = "hello fish & rabbit!!";
        System.out.println("发送消息内容: " + message);
        this.rabbitTemplate.convertAndSend("LuVx-TopicExchange", "rabbit.fish", message);
    }

    public void sendallinvaild() {
        String message = "你应该接收不到此消息!";
        System.out.println("发送消息内容: " + message);
        this.rabbitTemplate.convertAndSend("LuVx-TopicExchange", "rabbit1.fish1", message);
    }


}