package org.luvx.tools.web;

import lombok.extern.slf4j.Slf4j;

import org.luvx.boot.web.response.R;
import org.luvx.boot.tools.dao.entity.User;
import org.luvx.boot.tools.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = {"app"}, method = {RequestMethod.GET})
    public R<Object> index() {
        Optional<User> byId = userMapper.selectByPrimaryKey(10000L);
        return R.success("ok!"
                + byId.get()
        );
    }
}
