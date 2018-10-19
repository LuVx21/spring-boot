package org.luvx.service.impl;

import org.luvx.dao.UserDao;
import org.luvx.entity.User;
import org.luvx.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }
}
