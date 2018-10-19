package org.luvx.repository;

import java.util.List;

import org.luvx.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    // @Cacheable(key = "#p0", value = "key-User")
    User findById(long id);

    @Cacheable(value = "key-Users")
    List<User> findAll();

    // @CachePut(key = "#p0.id")
    @Override
    User save(User user);

    @Transactional
    @Modifying
    @CacheEvict(key = "#p0")
    void delete(long id);

    @Transactional
    @Modifying
    @CacheEvict(key = "#p0")
    Long deleteById(Long id);
}