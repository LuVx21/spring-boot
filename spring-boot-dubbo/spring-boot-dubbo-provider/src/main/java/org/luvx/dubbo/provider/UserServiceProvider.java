package org.luvx.dubbo.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.luvx.dubbo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class UserServiceProvider implements UserService {

    @Override
    public List<String> listNames(Long id) {
        List<String> list = new ArrayList<String>();
        list.add(String.format("LuVx_%d", id - 1));
        list.add(String.format("LuVx_%d", id));
        list.add(String.format("LuVx_%d", id + 1));
        return list;
    }
}