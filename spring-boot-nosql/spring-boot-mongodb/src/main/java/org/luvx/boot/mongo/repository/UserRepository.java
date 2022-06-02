package org.luvx.boot.mongo.repository;

import org.luvx.boot.mongo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

}
