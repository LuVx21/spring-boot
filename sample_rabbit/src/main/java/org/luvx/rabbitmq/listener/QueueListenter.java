package org.luvx.rabbitmq.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListenter implements MessageListener {

    private final static Logger LOGGER = LogManager.getLogger(QueueListenter.class.getName());

    @Override
    public void onMessage(Message message) {
        String str = "";
        try {
            LOGGER.info("\n监听到消息:\n" + message);
            str = new String(message.getBody(), "UTF-8");
            LOGGER.info("接收消息内容:" + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}