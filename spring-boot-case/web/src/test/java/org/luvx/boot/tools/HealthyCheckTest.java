package org.luvx.boot.tools;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.tools.dao.entity.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;

@Slf4j
class HealthyCheckTest extends BaseAppTests {
    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    void m1() {
        final long id = 1L;
        User user = new User();

        // user.setId(id);
        // user.setUserName("foo");
        // user.setPassword("bar");
        // user.setAge(20);
        // user.setBirthday(LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        // user.setUpdateTime(LocalDateTime.now());
        // mongoTemplate.save(user);


        Criteria criteria = Criteria
                .where("id").is(1L);
        Query query = Query.query(criteria);
        user = mongoTemplate.findOne(query, User.class);
        log.info("查询结果: {}", user);
    }

    @Test
    void m2() {
    }
}