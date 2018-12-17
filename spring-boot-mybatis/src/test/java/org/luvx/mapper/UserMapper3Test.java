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
public class UserMapper3Test {

    private List<Object> userIds;

    @Autowired
    private UserMapper3 userMapper;

    @Before
    public void initData() {
        userIds = Arrays.asList(3L, 4L, 5L);
    }

    @Test
    public void batDeleteByPrimaryKey() {
        int nums = userMapper.batDeleteByPrimaryKey(userIds);
        System.out.println(nums);
    }

    @Test
    public void batUpdateByPrimaryKey() {
        int nums = userMapper.batUpdateByPrimaryKey(userIds, "12340");
        System.out.println(nums);
    }

    @Test
    public void batSelectByPrimaryKey() {
        List<User> users = userMapper.batSelectByPrimaryKey(userIds);
        users.stream().forEach(System.out::println);
    }
}