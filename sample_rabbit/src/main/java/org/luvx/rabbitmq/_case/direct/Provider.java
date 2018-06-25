package org.luvx.rabbitmq._case.direct;

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
        channel.exchangeDeclare("LuVx-direct", "direct");
        channel.queueDeclare(RABBIT, false, false, false, null);
        channel.queueBind(RABBIT, "LuVx-direct", "rabbit");
        channel.queueDeclare(FISH, false, false, false, null);
        channel.queueBind(FISH, "LuVx-direct", "fish");


        channel.basicPublish("LuVx-direct", "rabbit", null, "Hello Rabbit!".getBytes());
        channel.basicPublish("LuVx-direct", "fish", null, "Hello fish!".getBytes());

        channel.close();
        connection.close();
    }

}
