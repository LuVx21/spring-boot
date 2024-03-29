package org.luvx.repository;

import org.junit.jupiter.api.Test;
import org.luvx.JpaAppTests;
import org.luvx.entity.User;

import java.util.Optional;

class UserRepositoryTest extends JpaAppTests {
    @Test
    void insertTest() {
        User user = new User()
                .setUserName("foo")
                .setPassword("bar")
                .setAge(18);
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
    void deleteTest() {
        User user = new User()
                .setId(10044L)
                .setUserName("foo")
                .setPassword("bar")
                .setAge(18);
        userRepository.delete(user);
    }

    @Test
    void selectTest() {
        Optional<User> user = userRepository.findById(1L);
        System.out.println(user);
    }
}