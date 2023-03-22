package org.luvx.boot.gradle.controller;

import org.luvx.boot.gradle.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {
    private final TestService service;

    @GetMapping("/test")
    public String test() {
        return service.test();
    }
}
