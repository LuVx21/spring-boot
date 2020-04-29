package org.luvx.transaction.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Ren, Xie
 * @desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    UserService service;

    @Test
    public void methodTest() {
        service.method();
    }

    @Test
    public void method1Test() {
        service.method1();
    }

    @Test
    public void method2est() {
        service.method2();
    }
}