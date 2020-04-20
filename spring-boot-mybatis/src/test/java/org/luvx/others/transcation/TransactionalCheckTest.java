package org.luvx.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionalCheckTest {
    @Autowired
    TransactionalCheck service;

    @Test
    public void testMethod() {
        service.method0();
    }
}