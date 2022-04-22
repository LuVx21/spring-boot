package org.luvx.boot.oauth2.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

@RestController
public class IndexController {
    @GetMapping(value = {"/"})
    public Object add(String s) {
        return ImmutableMap.of("a", "aaa");
    }
}
