package org.luvx.daoTest;


import org.junit.Test;
import org.luvx.BaseTest;
import org.luvx.dao.UserMapper;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * mybatis测试用
 *
 * @author renxie
 */
public class UserMapperTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void saveTest() {
        User user = new User();
        user.setUserName("RenXie");
        user.setPassword("1234");
        user.setAge(26);
        userMapper.save(user);
    }

    @Test
    public void findByIdTest() {
        User user = userMapper.findById(1L);
        System.out.println(user);
    }


    @Test
    public void findAllTest() {
        List<User> list = userMapper.findAll();
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void updateTest() {
        User user = userMapper.findById(1L);
        user.setAge(18);
        userMapper.update(user);
    }

    @Test
    public void deleteByIdTest() {
        userMapper.deleteById(2L);

    }

}
