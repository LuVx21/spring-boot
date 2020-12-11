package org.luvx.java.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.luvx.java.service.UserReadService;
import org.luvx.kotlin.entity.User;
import org.luvx.kotlin.repository.UserRepository;
import org.luvx.kotlin.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Slf4j
@Service
public class UserReadServiceImpl implements UserReadService {
    @Resource
    UserService    userService;
    @Resource
    UserRepository userRepository;

    @Override
    public User selectById(Long id) {
        log.info("查询:{}", id);
        Optional<User> op = userRepository.findById(id);
        return op.orElse(null);
    }

    @Override
    public void select(User user) {
        log.info("查询:{}", user);
        userService.delete(user);
    }
}
