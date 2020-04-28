package org.luvx.controller;

import org.luvx.pojos.User;
import org.luvx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: org.luvx.controller
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/11/6 10:20
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/insert")
    public void insert(User user) {
        userService.insert(user);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/{id}/select")
    public User select(@PathVariable("id")int id){
        return userService.selectById(id);
    }
}
