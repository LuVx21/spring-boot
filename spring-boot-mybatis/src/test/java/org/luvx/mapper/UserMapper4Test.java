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
        User user1 = new User(3L, "Luvx1", "1234_1", 20);
        User user2 = new User(4L, "Luvx2", "1234_2", 21);
        User user3 = new User(5L, "Luvx3", "1234_3", 22);
        users = Arrays.asList(user1, user2, user3);
    }

    @Test
    public void batInsert() {
        int nums = userMapper.batInsert(users);
        System.out.println(nums);
    }

    @Test
    public void batInsertSelective() {
        int nums = userMapper.batInsertSelective(users);
        System.out.println(nums);
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
        List<User> userList = userMapper.batSelectSelective(users);
        userList.stream().forEach(System.out::println);
    }
}