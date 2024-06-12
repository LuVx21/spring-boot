package org.luvx.boot.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.jdbc.entity.User;

import java.util.List;
import java.util.Optional;

@Slf4j
public class JdbcClientTest extends JdbcAppTests {

    @Test
    public void insertTest() {
        // User user = new User();
        // int inserted = jdbcClient.sql("insert into user (id, name, lastname, birthday) values (?,?,?,?)")
        //         .params(List.of(user.id(), user.name(), user.lastname(), user.birthday()))
        //         .update();
    }

    @Test
    public void updateTest() {
        // int updated = jdbcClient.sql("update user set name = ?, lastname = ?, birthday = ? where id = ?")
        //         .params(List.of(user.name(), user.lastname(), user.birthday(), id))
        //         .update();
    }

    @Test
    public void selectTest() {
        Optional<User> o = jdbcClient.sql("select * from user where id= :id")
                .param("id", 1L)
                .query(User.class)
                .optional();
        System.out.println(o.get());
    }
}
