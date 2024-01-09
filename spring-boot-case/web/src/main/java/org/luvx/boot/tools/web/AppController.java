package org.luvx.boot.tools.web;

import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.dao.entity.User;
import org.luvx.boot.tools.dao.mapper.UserMapper;
import org.luvx.boot.web.response.R;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AppController {
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private UserMapper    mapper;

    @RequestMapping(value = {"/info"}, method = {RequestMethod.GET})
    public R<Object> index() {
        return R.success("ok!");
    }

    @RequestMapping(value = {"/healthyCheck"}, method = {RequestMethod.GET})
    public R<Object> index1() {
        final long id = 1L;

        User u1 = mapper.selectByPrimaryKey(id).orElse(null);

        Criteria criteria = Criteria.where("id").is(id);
        User u2 = mongoTemplate.findOne(Query.query(criteria), User.class);
        return R.success(Lists.newArrayList("mysql", u1, "mongo", u2));
    }
}
