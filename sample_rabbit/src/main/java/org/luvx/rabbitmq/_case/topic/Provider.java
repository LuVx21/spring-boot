package org.luvx.rabbitmq._case.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Provider {
    private final static String RABBIT = "rabbit";
    private final static String FISH = "fish";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // exchange的名字,类型
        channel.exchangeDeclare("LuVx-topic", "topic");
        channel.queueDeclare(RABBIT, false, false, false, null);
        channel.queueBind(RABBIT, "LuVx-topic", "rabbit.*.*");
        channel.queueDeclare(FISH, false, false, false, null);
        channel.queueBind(FISH, "LuVx-topic", "fish.*.*");


        channel.basicPublish("LuVx-topic", "rabbit.xx.xx", null, "Hello Rabbit!".getBytes());
        channel.basicPublish("LuVx-topic", "fish.11.22", null, "Hello fish!".getBytes());
        // 第3条信息不会被分发到队列中,路由规则不符合任何绑定规则
        channel.basicPublish("LuVx-topic", "xx.11.33", null, "Hello Rabbit fish!".getBytes());

        channel.close();
        connection.close();
    }

}
