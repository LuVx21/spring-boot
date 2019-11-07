package org.luvx.module.controller;

import org.luvx.module.domain.User;
import org.luvx.module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", produces = "application/json")
    @ResponseBody
    public List<User> getUser() {
        return userService.getUser();
    }
    
    @PostMapping(value = "/user", consumes = "application/json")
    @ResponseBody
    public User add(@RequestBody User user) {
        return userService.save(user);
    }
}
