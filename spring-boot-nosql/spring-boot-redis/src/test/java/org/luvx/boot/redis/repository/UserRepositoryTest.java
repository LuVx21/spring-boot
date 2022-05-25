package org.luvx.boot.redis.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.ApplicationTests;
import org.luvx.boot.redis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
class UserRepositoryTest extends ApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void m0() {
        Stream.of(
                        new User("foo", "1121", 19),
                        new User("bar", "1121", 19)
                )
                .peek(u -> u.setId(System.currentTimeMillis()))
                .forEach(user -> {
                    User save = userRepository.save(user);
                    Optional<User> byId = userRepository.findById(user.getId());
                    log.info("save:{} find:{}", save, byId.get());
                });
    }
}