package org.luvx.service.impl;

import org.luvx.entity.User;
import org.luvx.repository.UserRepository;
import org.luvx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 使用Redis 缓存
 * 自动根据方法生成缓存
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    // @Cacheable(value = "key-Users")
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    @Cacheable(value = "key-User")
    public User findUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void edit(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(id);
    }
}


