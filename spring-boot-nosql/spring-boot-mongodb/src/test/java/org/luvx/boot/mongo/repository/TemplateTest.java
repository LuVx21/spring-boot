package org.luvx.boot.mongo.repository;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.mongo.MongoAppTests;
import org.luvx.boot.mongo.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
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

    @Test
    public void testCircle() {
        // 构建一个圆形范围
        Circle circle = new Circle(new GeoJsonPoint(116.404, 39.915), new Distance(0.8, Metrics.KILOMETERS));
        Query query = Query.query(Criteria.where("location").withinSphere(circle));
        List<User> list = mongoTemplate.find(query, User.class);
        list.forEach(System.out::println);
    }

    /**
     * 点范围内,由近到远进行排列
     */
    @Test
    public void testNearest() {
        NearQuery query = NearQuery
                .near(new GeoJsonPoint(116.404, 39.915))
                .maxDistance(new Distance(0.8, Metrics.KILOMETERS));

        // 由于 User 的字段 location 设置了空间索引，因此针对该字段与给定点进行距离计算并筛选。
        GeoResults<User> results = mongoTemplate.geoNear(query, User.class);
        for (GeoResult<User> result : results) {
            User u = result.getContent();
            double value = result.getDistance().getValue();
            System.out.println(u.getUserName() + " 距离给定点：" + (int) (value * 1000) + " 米");
        }
    }

    @Test
    public void testRectangle() {
        // 构建一个三角形（需要注意：多边形首尾两个点的坐标必须相同）
        List<Point> points = List.of(
                new Point(116.361, 39.924),
                new Point(116.415, 39.933),
                new Point(116.393, 39.891),
                new Point(116.361, 39.924)
        );

        GeoJsonPolygon polygon = new GeoJsonPolygon(points);
        Query query = Query.query(Criteria.where("location").within(polygon));
        List<User> list = mongoTemplate.find(query, User.class);
        list.forEach(System.out::println);
    }
}