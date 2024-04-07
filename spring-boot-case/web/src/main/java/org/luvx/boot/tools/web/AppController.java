package org.luvx.boot.tools.web;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.dao.entity.User;
import org.luvx.boot.tools.dao.mapper.UserMapper;
import org.luvx.boot.tools.service.sqlite.ChromeCookieService;
import org.luvx.boot.web.response.R;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class AppController {
    @Resource
    private   MongoTemplate                 mongoTemplate;
    @Resource
    protected RedisTemplate<String, Object> redisTemplate;
    @Resource
    protected StringRedisTemplate           stringRedisTemplate;
    @Resource
    private   UserMapper                    mapper;
    @Resource(name = "sqliteJdbcTemplate")
    private   JdbcTemplate                  jdbcTemplate;
    @Resource
    private   ChromeCookieService           cookieService;

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
}
