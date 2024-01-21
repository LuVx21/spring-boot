package org.luvx.boot.mongo.repository;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.mongo.MongoAppTests;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Slf4j
class TemplateTest extends MongoAppTests {
    String collectionName = "user";

    @Test
    void m1() {
        Criteria criteria = Criteria.where("userName").is("foo_0")
                // 无元素
                // .and("addressList").exists(true).size(0)
                // 有元素
                .and("addressList").exists(true).not().size(0);
        Query query = Query.query(criteria).with(Sort.by(Sort.Direction.DESC, "_id")).limit(100);
        query.fields().include("userName", "addressList");
        List<JSONObject> feedList = mongoTemplate.find(query, JSONObject.class, collectionName);
        System.out.println(feedList);
        JSONObject first = feedList.getFirst();
        System.out.println(first);
        System.out.println(first.getClass());
        System.out.println(first.getString("_id"));
    }
}