package org.luvx.module.user.service.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.luvx.base.BaseTest;
import org.luvx.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Ignore
public class UserServiceImplTest extends BaseTest {

    @Autowired
    private UserService service;

    @Test
    public void getByIdTest() {
        service.getUserById(0L);
    }
}