package org.luvx.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Ignore
    @Test
    public void run01() {
        User user = new User("qqq", "qqqq", 25);
        userMapper.save(user);
        // 不使用缓存
        userMapper.findById(1L);
        // 使用缓存
        User user1 = userMapper.findById(72L);
        // false
        System.out.println(user == user1);
    }
}
