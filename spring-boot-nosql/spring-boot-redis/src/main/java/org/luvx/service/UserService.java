package org.luvx.service;

import org.luvx.entity.User;


public interface UserService {

    User findById(int id);

    User save(User user);

    long delete(long id);

}
