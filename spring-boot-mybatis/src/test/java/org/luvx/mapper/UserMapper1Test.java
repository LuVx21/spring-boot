package org.luvx.mapper;

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
public class UserMapper1Test {
/*
    @Autowired
    private UserMapper1 userMapper;

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
        user = userMapper.getById(3l);
        System.out.println(user.toString());
    }

    @Test
    public void updateTest1() {
        User user = new User();
        user.setUserId(4l);
        user.setAge(23);
        System.out.println(user);
        int result = userMapper.update(user);
        System.out.println(result);
        user = userMapper.getById(4l);
        System.out.println(user);
    }

    @Test
    public void addTest() {
        User user = new User();
        user.setUserId(3l);
        user.setAge(23);
        System.out.println(user);
        userMapper.addSelective(user);
        user = userMapper.getById(3l);
        System.out.println(user);
    }

    @Test
    public void selectTest() {
        User user = userMapper.getById(3l);
        System.out.println(user);
    }

    @Test
    public void batInsertTest() {
        User user1 = new User("11111", "11111", 21);
        User user2 = new User("22222", "22222", 21);
        List<User> list = Arrays.asList(user1, user2);
        userMapper.batInsert(list);
    }

    @Test
    public void batUpdateTest() {
        List<String> list = Arrays.asList("11111", "22222");
        String pwd = "aaaaa";
        userMapper.batUpdateByUserName(list, pwd);
    }

    @Test
    public void batSelectTest() {
        User user1 = new User("11111", "11111", 21);
        User user2 = new User("22222", "22222", 21);
        List<User> list = Arrays.asList(user1, user2);
        List<User> result = userMapper.batSelect(list);
        result.stream().forEach(System.out::println);
    }
*/
}