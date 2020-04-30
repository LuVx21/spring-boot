package org.luvx.controller;

import org.luvx.entity.User;
import org.luvx.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/find/{id}")
    public Object query(@PathVariable int id) {
        return userService.findById(id);
    }

    @RequestMapping("/save")
    public Object save(@ModelAttribute User user) {
        return userService.save(user);
    }

    @RequestMapping("/delete/{id}")
    public Object delete(@PathVariable int id) {
        return userService.delete(id);
    }

    @RequestMapping("/queryPage")
    public Object query(String name, int pageNum, int count) {
        // Pageable pageable = new PageRequest(pageNum, count, Sort.Direction.DESC, "age");
        // return userRepository.findByName(name, pageable);
        return null;
    }
}
