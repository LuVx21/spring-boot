package org.luvx.boot.oauth2.authorize.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

@RestController
public class IndexController {
    @GetMapping(value = {"/"})
    public Object index(String s) {
        return ImmutableMap.of("index", "index");
    }
}
