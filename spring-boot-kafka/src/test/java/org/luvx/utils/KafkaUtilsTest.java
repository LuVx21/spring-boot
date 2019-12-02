package org.luvx.utils;

import org.junit.jupiter.api.Test;

import java.util.Properties;

/**
 * @ClassName: org.luvx.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/12/2 17:13
 */
public class KafkaUtilsTest {

    private Properties pro;

    {
        pro = new Properties();
        pro.put("kafka.topic.name", "foo");
        pro.put("kafka.producer.xx", "xx");
        pro.put("kafka.consumer.yy", "yy");
        pro.put("xx.yy.zz", "zz");
    }

    @Test
    public void retrieveProducerProperties() {
        Properties p = KafkaUtils.retrieveProperties(pro, "kafka.producer.");
        System.out.println(p);
    }
}