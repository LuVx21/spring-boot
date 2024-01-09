package org.luvx.boot.rpc.dubbo.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.luvx.boot.rpc.dubbo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserServiceConsumer {
    @DubboReference(version = "1.0.0", check = false)
    private UserService userService;

    @GetMapping("/index")
    public List<String> listNames() {
        return userService.listNames(System.currentTimeMillis());
    }
}
