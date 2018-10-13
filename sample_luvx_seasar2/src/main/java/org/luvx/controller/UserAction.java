package org.luvx.controller;

import com.opensymphony.xwork2.ActionSupport;
import org.luvx.entity.User;
import org.luvx.service.UserService;

import java.util.List;

public class UserAction extends ActionSupport {
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String list() {
        List<User> users = this.userService.getUsers();
        for (User uesr : users) {
            System.out.println(uesr.getUserName());
        }
        return SUCCESS;
    }
}
