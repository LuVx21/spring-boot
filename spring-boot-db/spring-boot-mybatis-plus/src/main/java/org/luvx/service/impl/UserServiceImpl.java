package org.luvx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.luvx.entity.User;
import org.luvx.mapper.UserMapper;
import org.luvx.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements UserService {

}
