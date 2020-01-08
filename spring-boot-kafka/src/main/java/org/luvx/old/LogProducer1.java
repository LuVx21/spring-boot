package org.luvx.old;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.luvx.utils.KafkaUtils;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    /**
     * 同步发送消息
     */
    public void send(String msg) {
        Properties props = KafkaUtils.getProducerProp();
        Producer<String, String> producer = new KafkaProducer<>(props);

        Future future = producer.send(new ProducerRecord<>(topic, null, null, msg));
        try {
            RecordMetadata data = (RecordMetadata) future.get();
        } catch (ExecutionException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        log.info("生产消息:{}", msg);
        producer.flush();
    }

    /**
     * 异步发送消息
     */
    public void send1(String msg) {
        Properties props = KafkaUtils.getProducerProp();
        Producer<String, String> producer = new KafkaProducer<>(props);

        producer.send(new ProducerRecord<>(topic, null, null, msg), new ProducerCallback());
    }

    private static class ProducerCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            //如果Kafka返回一个错误，onCompletion方法抛出一个non null异常。
            if (e != null) {
                e.printStackTrace();
            }
        }
    }
}
