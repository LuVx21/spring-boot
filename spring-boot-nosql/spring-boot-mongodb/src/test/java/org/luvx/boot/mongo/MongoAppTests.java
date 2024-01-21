package org.luvx.boot.mongo;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.boot.mongo.repository.UserRepository;
import org.luvx.boot.mongo.service.UserService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MongoAppTests {
    @Resource
    protected MongoTemplate  mongoTemplate;
    @Resource
    protected UserRepository userRepository;
    @Resource
    protected UserService    userService;
}
