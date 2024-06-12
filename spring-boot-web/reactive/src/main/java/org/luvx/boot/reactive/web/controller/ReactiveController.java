package org.luvx.boot.reactive.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.reactive.web.entity.UserVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/reactive")
public class ReactiveController {
    @RequestMapping(value = {"index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Mono<Object> index() {
        UserVo user = new UserVo();
        user.setId(10000L);
        user.setUserName("foo");
        user.setPassword("bar");
        return Mono.just(user);
    }
}
