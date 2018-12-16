package org.luvx.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
        userMapper.batDeleteByPrimaryKey(userIds);
    }

    @Test
    public void batUpdateByPrimaryKey() {
        userMapper.batUpdateByPrimaryKey(userIds);
    }

    @Test
    public void batSelectByPrimaryKey() {
        userMapper.batSelectByPrimaryKey(userIds);
    }
}