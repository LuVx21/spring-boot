package org.luvx.mongodb.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.mongodb.User;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class UserRepositoryTest extends ApplicationTests {
    @Autowired
    private UserRepository userDao;

    @Test
    public void testSaveUser() throws Exception {
        User user = new User();
        user.setId(100L);
        user.setUserName("foo");
        user.setPassword("bar");
        user.setAge(20);
        userDao.saveUser(user);
    }

    @Test
    public void findUserByUserName() {
        User user = userDao.findUserByUserName("foo");
        log.info("user:{}", user);
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(2l);
        user.setUserName("天空");
        user.setPassword("fffxxxx");
        userDao.updateUser(user);
    }

    @Test
    public void deleteUserById() {
        userDao.deleteUserById(1l);
    }
}