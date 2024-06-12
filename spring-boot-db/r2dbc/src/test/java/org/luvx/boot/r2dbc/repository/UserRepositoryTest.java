package org.luvx.boot.r2dbc.repository;

import org.junit.jupiter.api.Test;
import org.luvx.boot.r2dbc.BaseAppTests;
import org.luvx.boot.r2dbc.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.annotation.Resource;

class UserRepositoryTest extends BaseAppTests {
    @Resource
    private UserRepository repository;

    @Test
    public void select() {
        Mono<User> m = repository.findById(1L);
        System.out.println(m.block());

        Flux<User> foo = repository.findByEntity("foo");
        System.out.println(foo.blockFirst());
    }
}