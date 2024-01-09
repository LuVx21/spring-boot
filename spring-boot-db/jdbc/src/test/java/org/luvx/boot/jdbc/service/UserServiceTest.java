package org.luvx.boot.jdbc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService service;

    @Test
    void m0() {
        service.method0();
    }

    @Test
    void m1() {
        service.method1();
    }

    @Test
    void m2() {
        service.method2();
    }

    @Test
    void m3() {
        service.method3();
    }
}