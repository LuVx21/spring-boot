package org.luvx.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName: org.luvx.service.impl
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/7/16 16:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Resource
    @Qualifier("UserServiceImpl")
    UserService userService;

    @Test
    public void method() {
        userService.method();
    }
}