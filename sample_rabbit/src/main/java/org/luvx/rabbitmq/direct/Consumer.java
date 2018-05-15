package org.luvx.rabbitmq.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer {
    private final static String RABBIT = "rabbit";
    private final static String FISH = "fish";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("LuVx-direct", "direct");
        channel.queueDeclare(RABBIT, false, false, false, null);
        channel.queueBind(RABBIT, "LuVx-direct", "rabbit");
        channel.queueDeclare(FISH, false, false, false, null);
        channel.queueBind(FISH, "LuVx-direct", "fish");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(RABBIT, true, consumer);
        channel.basicConsume(FISH, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("收到的信息内容:" + message);
        }
    }
}