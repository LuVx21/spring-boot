package org.luvx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-service.xml", "classpath:applicationContext-dao.xml"})
public class BaseTest {
    @Test
    public void run() {
        System.out.println("this is base test of service!");
    }
}