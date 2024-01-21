package org.luvx.boot.mongo.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.mongo.MongoAppTests;
import org.luvx.boot.mongo.entity.User;
import org.springframework.data.domain.Example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
class UserRepositoryTest extends MongoAppTests {
    @Test
    void m0() {
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setId((long) i);
            user.setUserName(STR."foo_\{i}");
            user.setPassword(STR."bar_\{i}");
            user.setAge(i);
            user.setBirthday(LocalDateTime.now());
            user.setAddressList(List.of(STR."北京_\{i}", STR."上海_\{i}"));
            userRepository.save(user);
        }
    }

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