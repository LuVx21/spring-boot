package org.luvx.controller;

import org.luvx.entity.Post;
import org.luvx.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    @Autowired
    private PostServiceImpl postService;

    @RequestMapping("/query/{id}")
    public Object query(@PathVariable int id) {
        return postService.findById(id);
    }

    @RequestMapping("/save")
    public Object save(@ModelAttribute Post post) {
        return postService.save(post);
    }

    @RequestMapping("/delete/{id}")
    public Object delete(@PathVariable int id) {
        return postService.delete(id);
    }

    @RequestMapping("/queryPage")
    public Object query(String name, int pageNum, int count) {
        //根据weight倒序分页查询
        // Pageable pageable = new PageRequest(pageNum, count, Sort.Direction.DESC, "weight");
        // return userRepository.findByName(name, pageable);
        return null;
    }
}
