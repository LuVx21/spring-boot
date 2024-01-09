package org.luvx.boot.oauth2.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

@RestController
public class IndexController {
    @GetMapping(value = {"/user"})
    public Object add(String s) {
        return ImmutableMap.of(
                "username", "xiaoming",
                "user_cname", "小明",
                "role", "analyst"
        );
    }
}
