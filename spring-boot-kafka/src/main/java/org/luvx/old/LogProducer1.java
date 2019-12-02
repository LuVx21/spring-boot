package org.luvx.old;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.luvx.utils.KafkaUtils;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @ClassName: org.luvx._new
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/12/2 11:09
 */
@Slf4j
@Component
public class LogProducer1 {
    private static final String topic = "foo";

    public void send(String msg) {
        Properties props = KafkaUtils.getProducerProp();
        Producer<String, String> producer = new KafkaProducer<>(props);

        producer.send(new ProducerRecord<>(topic, null, null, msg));

        log.info("生产消息:{}", msg);
        producer.flush();
    }
}
