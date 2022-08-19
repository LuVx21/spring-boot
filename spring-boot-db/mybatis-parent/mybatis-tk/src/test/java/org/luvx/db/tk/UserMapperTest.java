package org.luvx.db.tk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.db.tk.entity.User;
import org.luvx.db.tk.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserMapperTest {
    @Autowired
    UserMapper mapper;

    @Test
    void m1() {
        List<User> list = mapper.wrapper()
                .eq(User::getId, 10000L)
                .list();
        System.out.println(list);
    }
}