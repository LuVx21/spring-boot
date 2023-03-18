package org.luvx.dubbo.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.luvx.dubbo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@DubboService(version = "1.0.0")
public class UserServiceImpl implements UserService {
    @Override
    public List<String> listNames(Long id) {
        List<String> list = new ArrayList<String>();
        list.add(String.format("LuVx_%d", id - 1));
        list.add(String.format("LuVx_%d", id));
        list.add(String.format("LuVx_%d", id + 1));
        return list;
    }
}