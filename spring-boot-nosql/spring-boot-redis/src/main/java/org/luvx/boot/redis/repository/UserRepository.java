package org.luvx.boot.redis.repository;

import org.luvx.boot.redis.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@CacheConfig(cacheNames = "user")
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Cacheable(key = "#p0")
    User findById1(long id);

    @CachePut(key = "#p0.id")
    User save1(User user);

    @CacheEvict(key = "#p0")
    long deleteById(long id);
}