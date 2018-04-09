package org.luvx.repository;

import org.luvx.entity.Post;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@CacheConfig(cacheNames = "post")
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    @Cacheable(key = "#p0")
    Post findById(int id);

    @CachePut(key = "#p0.id")
    @Override
    Post save(Post post);

    @Transactional
    @Modifying
    @CacheEvict(key = "#p0")
    int deleteById(int id);
}
