package org.luvx.boot.mongo.service;

import java.util.List;

import javax.annotation.Resource;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.junit.jupiter.api.Test;
import org.luvx.boot.mongo.ApplicationTests;
import org.luvx.boot.mongo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema.MongoJsonSchemaBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class UserServiceTest extends ApplicationTests {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Resource
    private UserService   userService;

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