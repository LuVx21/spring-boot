package org.luvx.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: org.luvx.repository
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/21 19:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void insertTest() {
        User user = User.builder().id(10044L).userName("foo").password("bar").age(18).build();
        user = userRepository.save(user);
        System.out.println(user);
        user.setAge(19);
        user = userRepository.saveAndFlush(user);
        System.out.println(user);

        List<User> list = new ArrayList<>();
        list.add(User.builder().id(10045L).userName("foo").password("bar").age(24).build());
        list.add(User.builder().id(10046L).userName("foo").password("bar").age(25).build());
        List<User> list1 = userRepository.saveAll(list);
        System.out.println(list1);
    }

    @Test
    public void deleteTest() {
        User user = User.builder().id(10044L).userName("foo").password("bar").age(18).build();
        userRepository.delete(user);
    }

    @Test
    public void selectTest() {
        Optional<User> user = userRepository.findById(10043L);
        System.out.println(user.get());
    }
}