package org.luvx.boot.mongo.repository;

import org.luvx.boot.mongo.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface ReactiveUserRepository extends ReactiveMongoRepository<User, Long> {
}
