package org.luvx.boot.mongo.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.mongo.ApplicationTests;
import org.luvx.boot.mongo.entity.User;

import javax.annotation.Resource;
import java.util.Optional;

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
        userRepository.save(user);

        Optional<User> byId = userRepository.findById(id);

        user.setPassword("******");
        user.setAge(30);
        userRepository.save(user);
        log.info("find {} update {}", byId.get(), userRepository.findById(id).get());

        userRepository.deleteById(id);
    }
}