package org.luvx.boot.mars.web;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.luvx.boot.mars.dao.entity.User;
import org.luvx.boot.mars.dao.mapper.UserMapper;
import org.luvx.boot.mars.rpc.common.count.CountOperateType;
import org.luvx.boot.mars.rpc.proto.common.Int64;
import org.luvx.boot.mars.rpc.proto.user.UserResponse;
import org.luvx.boot.mars.rpc.proto.user.UserRpcService;
import org.luvx.boot.mars.rpc.sdk.count.CountRpcClient;
import org.luvx.boot.mars.rpc.sdk.user.UserCountType;
import org.luvx.boot.mars.service.count.CountEvent;
import org.luvx.boot.mars.service.kafka.KafkaTopics;
import org.luvx.boot.mars.service.sqlite.ChromeCookieService;
import org.luvx.boot.web.response.R;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.luvx.boot.mars.rpc.sdk.user.UserCountType.FANS_COUNT;

@Slf4j
@RestController
public class AppController {
    @Resource
    private   MongoTemplate                     mongoTemplate;
    @Resource
    protected RedisTemplate<String, Object>     redisTemplate;
    @Resource
    protected StringRedisTemplate               stringRedisTemplate;
    @Resource
    private   UserMapper                        mapper;
    @Resource(name = "sqliteJdbcTemplate")
    private   JdbcTemplate                      jdbcTemplate;
    @Resource(name = "sqliteJdbcClient")
    private   JdbcClient                        sqliteJdbcClient;
    @Resource
    private   ChromeCookieService               cookieService;
    @Resource
    private   KafkaTemplate<String, CountEvent> kafkaTemplate;

    @DubboReference(version = "1.0.0", check = false)
    private CountRpcClient countRpcClient;
    @DubboReference(version = "1.0.0", check = false)
    private UserRpcService userService;


    @RequestMapping(value = {"/", "/info"}, method = {RequestMethod.GET})
    public R<Object> index() {
        return R.success("ok!");
    }

    @RequestMapping(value = {"/app/healthyCheck"}, method = {RequestMethod.GET})
    public R<Object> index1() throws Exception {
        final long id = 1L;

        User u1 = mapper.selectByPrimaryKey(id).orElse(null);

        Criteria criteria = Criteria.where("id").is(id);
        User u2 = mongoTemplate.findOne(Query.query(criteria), User.class);

        Object o = stringRedisTemplate.opsForValue().get("foo");

        Map<String, Object> map = jdbcTemplate.queryForMap("select * from user where id = ?", id);
        Map<String, String> cookie = cookieService.getCookieByHost(List.of(".weibo.com", "weibo.com"), null);
        return R.success(Map.of("mysql", u1, "mongo", u2, "redis", o, "sqlite", map,
                "cookie", cookie
        ));
    }

    @RequestMapping(value = {"/app/healthyCheck/kafka"}, method = {RequestMethod.GET})
    public R<Object> kafka() throws Exception {
        var countId = 1L;
        CountEvent.CountEventData data = new CountEvent.CountEventData(countId, FANS_COUNT, 1);
        CountEvent event = new CountEvent(CountOperateType.INC_COUNT, data);
        CompletableFuture<SendResult<String, CountEvent>> send = kafkaTemplate.send(KafkaTopics.topic_count, countId + "", event);
        return R.success(Map.of("sendResult", JSON.toJSONString(send.get())));
    }

    @RequestMapping(value = {"/app/healthyCheck/dubbo"}, method = {RequestMethod.GET})
    public R<Object> dubbo() throws Exception {
        long i = countRpcClient.getByType(UserCountType.FANS_COUNT, 1L);

        Int64 id = Int64.newBuilder().setData(1L).build();
        UserResponse u = userService.getById(id);

        Map<String, ? extends Serializable> uu = Map.of(
                "id", u.getId(),
                "userName", u.getUserName(),
                "password", u.getPassword(),
                "age", u.getAge(),
                "birthday", u.getBirthday().toString()
        );
        return R.success(Map.of("count", i, "user", uu));
    }
}
