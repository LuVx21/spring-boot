package org.luvx.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: org.luvx.mapper
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 17:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void selectTest() {
        User user = userMapper.selectByPrimaryKey(10043L);
        System.out.println(user);
    }

}