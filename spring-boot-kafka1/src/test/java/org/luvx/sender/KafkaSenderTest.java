package org.luvx.sender;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @ClassName: org.luvx.sender
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/29 11:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaSenderTest {

    @Autowired
    private KafkaSender sender;

    @Test
    public void sendTest() {
        sender.send("ren");
    }
}