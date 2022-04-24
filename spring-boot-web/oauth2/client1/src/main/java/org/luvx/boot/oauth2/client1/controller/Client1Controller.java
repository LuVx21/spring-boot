package org.luvx.boot.oauth2.client1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

@RestController
public class Client1Controller {
    @GetMapping(value = {"/callback"})
    public Object callback(@RequestParam String code) {
        return ImmutableMap.of(
                "code", code,
                "app", "client1"
        );
    }
}
