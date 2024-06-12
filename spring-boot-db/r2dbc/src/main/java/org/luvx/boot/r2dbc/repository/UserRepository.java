package org.luvx.boot.r2dbc.repository;

import org.luvx.boot.r2dbc.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {
    @Query("select * from user where user_name = :userName")
    Flux<User> findByEntity(@Param("userName") String userName);
}