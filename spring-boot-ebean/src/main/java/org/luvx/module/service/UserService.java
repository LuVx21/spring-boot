package org.luvx.module.service;

import io.ebean.EbeanServer;
import io.ebean.annotation.Transactional;
import org.luvx.module.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    EbeanServer server;

    public List<User> getUser() {
        return server.find(User.class).findList();
    }

    @Transactional
    public User save(User user) {
        server.save(user);
        if (user.getUserName().equals("rollback")) {
            throw new RuntimeException("boooom!!");
        }
        return user;
    }
}
