package org.luvx.boot.rpc.dubbo.service.impl.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.luvx.boot.rpc.dubbo.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO 方式调用服务
 */
@DubboService(version = "1.0.0")
public class UserServiceImpl implements UserService {
    @Override
    public List<String> listNames(Long id) {
        List<String> list = new ArrayList<>();
        list.add(String.format("LuVx_%d", id - 1));
        list.add(String.format("LuVx_%d", id));
        list.add(String.format("LuVx_%d", id + 1));
        return list;
    }
}