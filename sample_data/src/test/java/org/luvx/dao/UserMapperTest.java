package org.luvx.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration({"classpath:applicationContext.xml", "classpath:applicationContext-dao.xml", "classpath:applicationContext-redis.xml"})
// @ContextConfiguration({"classpath:applicationContext-dao.xml", "classpath:applicationContext-redis.xml"})
@ContextConfiguration({"classpath:applicationContext.xml"})
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void run01() {
        userMapper.findById(68L);
    }


}
