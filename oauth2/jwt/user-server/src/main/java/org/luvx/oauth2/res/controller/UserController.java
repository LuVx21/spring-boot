package org.luvx.oauth2.res.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/hello")
    public String hello() {
        return "hello" + LocalDateTime.now();
    }

    @GetMapping("/admin/hello")
    public String admin() {
        return "admin";
    }
}
