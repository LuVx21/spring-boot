package org.luvx.demo1;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.luvx.ApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

public class KafkaSenderTest extends ApplicationTests {
    @Autowired
    KafkaSender sender;
    @Autowired
    KafkaReceiver receiver;

    ConsumerRecord<?, ?> record = new ConsumerRecord("F.LuVx", 0, 0, null, null);

    @Test
    public void sendTest() {
        sender.send("ren");
    }

    @Test
    public void listen() {
        receiver.listen(record);
    }
}