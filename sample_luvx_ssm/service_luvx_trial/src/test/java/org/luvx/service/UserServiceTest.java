package org.luvx.service;

import org.junit.Test;
import org.luvx.BaseTest;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserListTest() {
        List<User> list = userService.getUserList();
        for (User user : list) {
            System.out.println(user);
        }
        System.out.println(list.size());
    }
}
