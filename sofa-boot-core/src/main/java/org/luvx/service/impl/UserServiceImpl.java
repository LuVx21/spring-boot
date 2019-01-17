package org.luvx.service.impl;

import org.luvx.service.UserService;
import org.springframework.stereotype.Component;

@Component("testServiceImpl")
public class UserServiceImpl implements UserService {
    public String getUser(String id) {
        return "userid: " + id;
    }
}
