package org.luvx.boot.mongo.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.mongo.ApplicationTests;
import org.luvx.boot.mongo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@Slf4j
class UserServiceTest extends ApplicationTests {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testSaveUser() {
        User user = new User();
        user.setId(100L);
        user.setUserName("foo");
        user.setPassword("bar");
        user.setAge(20);
        mongoTemplate.save(user);
    }

    @Test
    void findUserByUserName() {
        Query query = Query.query(Criteria
                .where("userName").is("foo")
        );
        User user = mongoTemplate.findOne(query, User.class);
        log.info("user:{}", user);
    }

    @Test
    void updateUser() {
        Query query = Query.query(Criteria
                .where("id").is(100L)
        );
        Update update = Update
                .update("userName", "天空")
                .set("password", "1234567")
                .set("age", 30)
                .set("updateUser", "luvx");
        // 更新查询返回结果集的第一条
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
        // 更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query, update, User.class);
        long matchedCount = result.getMatchedCount();

        log.info("update {} {}", matchedCount, result);
    }

    @Test
    void deleteUserById() {
        Query query = Query.query(Criteria.where("id").is(100L));
        DeleteResult remove = mongoTemplate.remove(query, User.class);
        log.info("remove {}", remove);
    }
}