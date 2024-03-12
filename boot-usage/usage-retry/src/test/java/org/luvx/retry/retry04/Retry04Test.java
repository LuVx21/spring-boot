package org.luvx.retry.retry04;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Ren, Xie
 * @desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableRetry
public class Retry04Test {
    @Autowired
    Retry04 retry04;

    @Test
    public void test() {
        retry04.call();
    }
}