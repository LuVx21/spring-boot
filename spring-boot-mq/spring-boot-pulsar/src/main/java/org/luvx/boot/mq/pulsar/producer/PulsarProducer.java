package org.luvx.boot.mq.pulsar.producer;

import org.apache.pulsar.client.api.PulsarClientException;
import org.luvx.boot.mq.pulsar.config.User;
import org.luvx.boot.mq.pulsar.config.ConstValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PulsarProducer {
    @Autowired
    private PulsarTemplate<String> stringTemplate;
    @Autowired
    private PulsarTemplate<User>   template;

    public void sendString2Topic(String str) throws PulsarClientException {
        stringTemplate.send(ConstValue.STRING_TOPIC, str);
    }

    public void sendUser2Topic(User user) throws PulsarClientException {
        // template.send(PulsarConsumer.USER_TOPIC, user);
        template.newMessage(user)
                // 可用于配置消息延迟、在特定时间发送、禁用复制
                .withMessageCustomizer(mc -> mc.deliverAfter(10L, TimeUnit.SECONDS))
                // 可用于添加访问模式、自定义消息路由和拦截器，以及启用或禁用分块（chunking）和批处理
                // .withProducerCustomizer(pc -> pc.accessMode(ProducerAccessMode.Shared))
                .send();
    }

}