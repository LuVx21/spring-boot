package org.luvx.utils;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

/**
 * @ClassName: org.luvx.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/29 15:45
 */
public class KafkaUtilsTest {
    @Test
    public void convertToPropertiesTest() {
        Resource resource = new ClassPathResource("kafka.properties");
        Properties properties = KafkaUtils.convertToProperties(resource);
        System.out.println(properties);
    }
}