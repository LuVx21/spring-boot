package org.luvx.service.impl;

import org.luvx.entity.User;
import org.luvx.repository.UserRepository;
import org.luvx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(int id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public long delete(long id) {
        return userRepository.deleteById(id);
    }

}


