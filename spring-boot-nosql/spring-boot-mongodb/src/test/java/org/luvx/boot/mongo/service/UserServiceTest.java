package org.luvx.boot.mongo.service;

import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.mongo.MongoAppTests;
import org.luvx.boot.mongo.entity.User;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@Slf4j
class UserServiceTest extends MongoAppTests {
    @Test
    void m1() {
        Criteria age = Criteria.where("age").is(30);
        long count = userService.count(age);
        System.out.println(count);
    }

    @Test
    void findUserByUserName() {
        Criteria criteria = Criteria
                .where("age").is(300);

        // MongoJsonSchema jsonSchema = MongoJsonSchema.builder()
        //         .build();
        // Criteria criteria = Criteria.matchingDocumentStructure(jsonSchema);

        List<User> users = userService.list(criteria);
        log.info("users:{}", users);
    }

    @Test
    void updateUser() {
        String json = """
                {"id":101,"userName":"foo","password":"bar","age":20}
                """;
        Criteria criteria = Criteria
                .where("id").is(100L);

        Update update = Update
                .update("userName", "天空")
                .set("password", "1234567")
                .set("age", 30)
                .set("updateUser", "luvx")
                .set("json", json);
        userService.update(criteria, update);

        // 更新查询返回结果集的第一条
        Query query = Query.query(criteria);
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
        long matchedCount = result.getMatchedCount();
        log.info("update {} {}", matchedCount, result);
    }
}