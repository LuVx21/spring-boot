package org.luvx.tools.web.base.web;

import lombok.extern.slf4j.Slf4j;

import org.luvx.boot.web.enums.CommonStatusEnum;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/base")
public class BaseIndexController {
    @RequestMapping(value = {"index", "app"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Object index() {
        UserVo user = new UserVo();
        user.setUserId(10000L);
        user.setUserName("foo");
        user.setPassword("bar");
        user.setAge(18);
        user.setValid(2);
        return user;
    }

    @PostMapping("post")
    public Object post(@RequestBody UserVo user) {
        log.info("入参:{}", user);

        user.setAge(18);
        user.setStatus(CommonStatusEnum.VALID);
        return user;
    }
}
