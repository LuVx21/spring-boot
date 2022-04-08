package org.luvx.dubbo.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.luvx.dubbo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserServiceConsumer {
    @DubboReference(version = "1.0.0")
    private UserService userService;

    @GetMapping("/listnames")
    public List<String> listNames() {
        return userService.listNames(System.currentTimeMillis());
    }
}
