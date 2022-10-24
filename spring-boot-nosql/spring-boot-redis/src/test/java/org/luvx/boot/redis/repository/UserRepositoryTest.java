package org.luvx.boot.redis.repository;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.ApplicationTests;
import org.luvx.boot.redis.entity.User;
import org.luvx.coding.common.more.MorePrints;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class UserRepositoryTest extends ApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void m0() {
        Stream.of(
                        new User(1, "foo", "1121", 19),
                        new User(2, "bar", "1121", 19)
                )
                .forEach(user -> {
                    User save = userRepository.save(user);
                    Optional<User> byId = userRepository.findById(user.getId());
                    log.info("save:{} find:{}", save, byId.get());
                });
    }

    @Test
    void m2() {
        Optional<User> byId = userRepository.findById(1L);
        Optional<User> byId1 = userRepository.findById(2L);
        MorePrints.println(byId, byId1);
    }
}