package org.luvx.rabbitmq.producer;

public interface MQProducer
{
    /**
     * 发送消息到指定队列
     * @param routingKey
     * @param object
     */
    void sendDataToQueue(String routingKey, Object object);
}