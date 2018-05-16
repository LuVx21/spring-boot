package org.luvx.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListenter implements MessageListener {

    @Override
    public void onMessage(Message message) {
        String str = "";
        try {
            System.out.println("监听【QueueListenter】消息\n" + message);
            str = new String(message.getBody(), "UTF-8");
            System.out.print("获取到的消息:" + str);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}