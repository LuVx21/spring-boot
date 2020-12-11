package org.luvx.java.service;

import org.luvx.kotlin.entity.User;

public interface UserReadService {

    User selectById(Long id);

    void select(User user);
}
