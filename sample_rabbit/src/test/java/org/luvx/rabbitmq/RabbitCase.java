package org.luvx.rabbitmq;

import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

public class RabbitCase {

    com.rabbitmq.client.ConnectionFactory cfac;
    ConnectionFactory factory;
    SimpleMessageListenerContainer container;

    @Before
    public void init() {
        cfac = new com.rabbitmq.client.ConnectionFactory();
        cfac.setHost("localhost");
        cfac.setPort(5672);
        cfac.setVirtualHost("/");
        cfac.setUsername("guest");
        cfac.setPassword("guest");
        factory = new CachingConnectionFactory(cfac);
        RabbitAdmin admin = new RabbitAdmin(factory);
        Queue queue = new Queue("rabbit");
        TopicExchange exchange = new TopicExchange("myExchange");
        admin.declareQueue(queue);
        admin.declareExchange(exchange);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("rabbit.*"));
        container = new SimpleMessageListenerContainer(factory);
    }

    @Test
    public void run() throws Exception {

        Object listener = new Object() {
            public void handleMessage(String message) {
                System.out.println("接收消息内容:" + message);
            }
        };
        MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
        container.setMessageListener(adapter);
        container.setQueueNames("rabbit");
        container.start();

        send();
        container.stop();
    }

    public void send() {
        String message = "hello rabbit!";
        RabbitTemplate template = new RabbitTemplate(factory);
        template.convertAndSend("myExchange", "rabbit.bar", message);
    }
}