package org.luvx.module.user.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService service;

    @Test
    public void getByIdTest() {
        service.getUserById(0L);
    }
}