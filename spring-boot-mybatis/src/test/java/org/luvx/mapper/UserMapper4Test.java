package org.luvx.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapper4Test {
    private List<User> users;

    @Autowired
    private UserMapper4 userMapper;

    @Before
    public void initData() {
        User user1 = new User(3L);
        User user2 = new User(4L);
        User user3 = new User(5L);
        users = Arrays.asList(user1, user2, user3);
    }

    @Test
    public void batInsert() {
        userMapper.batInsert(users);
    }

    @Test
    public void batInsertSelective() {
        userMapper.batInsertSelective(users);
    }

    @Test
    public void batDeleteSelective() {
        userMapper.batDeleteSelective(users);
    }

    @Test
    public void batUpdateSelective() {
        userMapper.batUpdateSelective(users);
    }

    @Test
    public void batSelectSelective() {
        userMapper.batSelectSelective(users);
    }
}