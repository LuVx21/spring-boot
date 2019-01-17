package org.luvx.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapper5Test {
    @Autowired
    private UserMapper5 userMapper;

    @Test
    public void selectSelective() {
        User user = new User();
        user.setUserId(9999L);
        List<User> list = userMapper.selectSelective(user);
        list.stream().forEach(System.out::println);
    }
}