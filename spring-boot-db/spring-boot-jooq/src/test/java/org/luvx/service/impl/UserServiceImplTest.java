package org.luvx.service.impl;

import org.luvx.pojos.User;
import org.luvx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName: org.luvx.service.impl
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/11/6 10:14
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    UserService service;

    @Test
    public void insert() {
        User user = new User();
        user.setAge(102);
        user.setPassword("xxx");
        user.setUserName("xxxx");
        service.insert(user);
    }

    @Test
    public void select() {
        service.selectById(10055);
    }
}