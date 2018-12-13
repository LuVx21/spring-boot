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
public class UserMapper2Test {

    @Autowired
    private UserMapper2 userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUserId(9999L);
        user.setUserName("Luvx");
        user.setPassWord("0000");
        user.setAge(26);
        userMapper.insert(user);
    }

    @Test
    public void insertSelective() {
        User user = new User();
        user.setUserId(9999L);
        List<User> list = userMapper.selectSelective(user);
        user = list.get(0);
        user.setUserId(10000L);
        user.setAge(27);
        userMapper.insertSelective(user);
    }

    @Test
    public void deleteSelective() {
        User user = new User();
        user.setUserId(10000L);
        userMapper.deleteSelective(user);
    }

    @Test
    public void updateSelective() {
        User user = new User();
        user.setUserId(9999L);
        List<User> list = userMapper.selectSelective(user);
        user = list.get(0);
        user.setAge(user.getAge() + 1);
        userMapper.updateSelective(user);
    }

}