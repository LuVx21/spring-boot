package org.luvx.receiver;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @ClassName: org.luvx.receiver
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/29 11:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaReceiverTest {

    @Autowired
    private KafkaReceiver receiver;

    ConsumerRecord<?, ?> record = new ConsumerRecord("F.LuVx", 0, 0, null, null);

    @Test
    public void listen() {
        receiver.listen(record);
    }
}