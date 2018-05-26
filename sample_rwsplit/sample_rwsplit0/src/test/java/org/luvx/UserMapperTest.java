package org.luvx;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.dao.UserMapper;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class UserMapperTest {


    @Autowired
    UserMapper mapper;


    @Test
    public void run01() {
        User user = mapper.findById(1L);
        System.out.println(user);
    }

}
