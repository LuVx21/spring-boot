package org.luvx.boot.jdbc.repository;

import org.luvx.boot.jdbc.entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * 自定义查询方式
     *
     * @param userName
     * @return
     */
    @Query("select * from user where user_name = :userName")
    Stream<User> findByEntity(@Param("userName") String userName);
}