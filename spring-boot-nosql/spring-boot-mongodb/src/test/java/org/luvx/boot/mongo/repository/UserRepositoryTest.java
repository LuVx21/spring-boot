package org.luvx.boot.mongo.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.luvx.boot.mongo.ApplicationTests;
import org.luvx.boot.mongo.entity.User;
import org.springframework.data.domain.Example;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class UserRepositoryTest extends ApplicationTests {
    @Resource
    private UserRepository userRepository;

    @Test
    void m1() {
        User user = new User();
        long id = 200L;
        user.setId(id);
        user.setUserName("foo");
        user.setPassword("bar");
        user.setAge(20);
        user.setBirthday(LocalDateTime.now());
        userRepository.save(user);

        Optional<User> byId = userRepository.findById(id);

        user.setPassword("******");
        user.setAge(30);
        userRepository.save(user);
        log.info("find {} update {}", byId.get(), userRepository.findById(id).get());

        // userRepository.deleteById(id);
    }

    @Test
    void m2() {
        User user = new User();
        user.setId(100L);
        // user.setUserName("foo");
        Optional<User> one = userRepository.findOne(
                Example.of(user)
        );
        one.ifPresent(System.out::println);

        System.out.println("----------");

        Optional<User> byId = userRepository.findById(100L);
        byId.ifPresent(System.out::println);
    }
}