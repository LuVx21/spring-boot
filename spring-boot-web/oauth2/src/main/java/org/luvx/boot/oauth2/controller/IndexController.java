package org.luvx.boot.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

@RestController
public class IndexController {
    @GetMapping(value = {"/"})
    public Object add(String s) {
        return ImmutableMap.of("a", "aaa");
    }

    @GetMapping(value = {"/callback"})
    public Object callback(@RequestParam String code) {
        return ImmutableMap.of("code", code);
    }
}
