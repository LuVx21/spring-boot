package org.luvx.boot.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.boot.jdbc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository repository;

    @Test
    public void upsert() {
        List<User> users = Lists.newArrayList(
                User.builder().id(20004L).userName("foo").password("bar").age(18).build(),
                User.builder().id(20006L).userName("foo").password("bar").age(19).build()
        );
        Iterable<User> s = repository.saveAll(users);
        s.forEach(user -> log.info(user.toString()));
    }

    /**
     * 删除操作内部还是使用主键删除
     * 其他属性不起作用
     */
    @Test
    public void delete() {
        repository.delete(User.builder().id(10000L).build());
        List<User> list = new ArrayList<>();
        list.add(User.builder().id(20001L).userName("foo").password("bar").age(18).build());
        list.add(User.builder().id(20003L).userName("foo").password("bar").age(19).build());
        repository.deleteAll(list);
    }

    @Test
    public void select0() {
        Iterable<User> all = repository.findAll();
        all.forEach(s -> log.info(s.toString()));

        Optional<User> byId = repository.findById(1_0000L);
        log.info(byId.get().toString());
    }

    @Test
    public void select() {
        Stream<User> byEntity = repository.findByEntity("foo");
        byEntity.forEach(System.out::println);
    }
}