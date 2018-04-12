package org.luvx.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.luvx.entity.User;
import org.luvx.mapper.UserMapper;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/add")
    public void add(User user) {
        userMapper.add(user);
    }

    @RequestMapping(value = "/delete/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userMapper.deleteById(id);
    }

    @RequestMapping(value = "/delete/{userName}")
    public void deleteByUserName(@PathVariable("userName") String userName) {
        userMapper.deleteByUserName(userName);
    }

    @RequestMapping(value = "update")
    public void update(User user) {
        userMapper.update(user);
    }

    @RequestMapping("/getAll")
    public List<User> getAll() {
        List<User> users = userMapper.getAll();
        return users;
    }

    @RequestMapping("/getUser")
    public User getById(Long id) {
        User user = userMapper.getById(id);
        return user;
    }
}