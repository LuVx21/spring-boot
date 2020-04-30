package org.luvx.repository;

import org.luvx.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;


@CacheConfig(cacheNames = "user")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Cacheable(key = "#p0")
    User findById(long id);

    @CachePut(key = "#p0.id")
    @Override
    User save(User user);

    @Transactional
    @Modifying
    @CacheEvict(key = "#p0")
    long deleteById(long id);
}