package org.luvx.service.impl;

import org.luvx.entity.Post;
import org.luvx.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl {
    @Autowired
    private PostRepository postRepository;

    public Post findById(int id) {
        return postRepository.findById(id);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public int delete(int id) {
        return postRepository.deleteById(id);
    }
}
