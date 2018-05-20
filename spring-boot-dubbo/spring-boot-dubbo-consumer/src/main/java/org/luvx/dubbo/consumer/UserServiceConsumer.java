package org.luvx.dubbo.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import org.luvx.dubbo.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserServiceConsumer {
    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:20880")
    private UserService userService;

    @RequestMapping("/listnames")
    public List<String> listNames(@RequestParam Long id) {
        return userService.listNames(id);
    }

}
