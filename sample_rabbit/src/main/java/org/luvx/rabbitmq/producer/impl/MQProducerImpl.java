package org.luvx.rabbitmq.producer.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luvx.rabbitmq.producer.MQProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQProducerImpl implements MQProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private final static Logger LOGGER = LogManager.getLogger(MQProducerImpl.class.getName());

    public void sendDataToQueue(String queueKey, Object object) {
        try {
            LOGGER.info("=========发送消息开始=============\n发送消息内容：" + object.toString());
            amqpTemplate.convertAndSend(queueKey, object);
        } catch (Exception e) {
            LOGGER.error(e);
        }

    }
}