package org.luvx.service;

import io.ebean.EbeanServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.module.domain.User;
import org.luvx.module.domain.query.QUser;
import org.luvx.module.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    EbeanServer server;
    @Autowired
    UserRepository userRepository;

    @Test
    public void serverCheck() {
        System.out.println(server);
    }

    @Test
    public void insertTest() {
        User user = new User();
        user.setUserName("foo");
        user.save();
        // server.save(user);
        // userRepository.save(user);
    }

    @Test
    public void deleteTest() {
        User user = new User();
        user.setUserId((long) 1);
        user.delete();
    }

    @Test
    public void updateTest() {
        User user = new User();
        user.setUserId((long) 1);
        user.setUserName("F.LuVx");
        user.update();
    }

    @Test
    public void selectTest() {
        User user = userRepository.byId((long) 1);
        System.out.println(user);

        user = User.find.byId((long) 1);
        System.out.println(user);

        user = userRepository.byId((long) 1);
        System.out.println(user);
    }

    @Test
    public void selectTest1() {
        User user = new QUser().userName.equalTo("foo").findOne();
        System.out.println(user);
    }
}