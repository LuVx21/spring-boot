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
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteTest() {
        userMapper.deleteByUserName("LuVx");
        List<User> users = userMapper.getAll();
        System.out.println(users.toString());
        // user_name 有唯一性约束
        userMapper.add(new User("LuVx", "1121", 25));
        users = userMapper.getAll();
        System.out.println(users.toString());
    }

    @Test
    public void updateTest() {
        User user = userMapper.getById(3l);
        System.out.println(user.toString());
        user.setAge(23);
        userMapper.update(user);
    }
}