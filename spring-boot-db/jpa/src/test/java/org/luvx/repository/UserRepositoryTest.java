package org.luvx.repository;

import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserRepositoryTest extends ApplicationTests {
    @Autowired
    UserRepository userRepository;

    @Test
    public void insertTest() {
        User user = User.builder()
                // .id(10034L)
                .userName("foo").password("bar").age(18).build();
        user = userRepository.save(user);
        System.out.println(user);
        // user.setAge(19);
        // user = userRepository.saveAndFlush(user);
        // System.out.println(user);
        //
        // List<User> list = new ArrayList<>();
        // list.add(User.builder().id(10045L).userName("foo").password("bar").age(24).build());
        // list.add(User.builder().id(10046L).userName("foo").password("bar").age(25).build());
        // List<User> list1 = userRepository.saveAll(list);
        // System.out.println(list1);
    }

    @Test
    public void deleteTest() {
        User user = User.builder().id(10044L).userName("foo").password("bar").age(18).build();
        userRepository.delete(user);
    }

    @Test
    public void selectTest() {
        Optional<User> user = userRepository.findById(1L);
        System.out.println(user);
    }
}