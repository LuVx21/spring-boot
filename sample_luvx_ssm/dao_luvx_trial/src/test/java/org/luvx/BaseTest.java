package org.luvx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-dao_MyBatis.xml"})
public class BaseTest {

    @Test
    public void run() {
        System.out.println("this basetest of dao!");
    }

}