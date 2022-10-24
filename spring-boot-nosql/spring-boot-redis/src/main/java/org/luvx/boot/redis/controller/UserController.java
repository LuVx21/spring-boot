package org.luvx.boot.redis.controller;

import org.luvx.boot.redis.entity.User;
import org.luvx.boot.redis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository repository;

    @RequestMapping("/find/{id}")
    public Object query(@PathVariable long id) {
        return repository.findById(id);
    }

    @RequestMapping("/save")
    public Object save(@ModelAttribute User user) {
        return repository.save(user);
    }

    // @RequestMapping("/delete/{id}")
    // public Object delete(@PathVariable int id) {
    //     return userService.delete(id);
    // }
    //
    // @RequestMapping("/queryPage")
    // public Object query(String name, int pageNum, int count) {
    //     Pageable pageable = new PageRequest(pageNum, count, Sort.Direction.DESC, "age");
    //     return userRepository.findByName(name, pageable);
    // }
}
