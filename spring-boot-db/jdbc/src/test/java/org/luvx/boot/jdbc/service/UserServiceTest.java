package org.luvx.boot.jdbc.service;

import org.junit.jupiter.api.Test;
import org.luvx.boot.jdbc.JdbcAppTests;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends JdbcAppTests {
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