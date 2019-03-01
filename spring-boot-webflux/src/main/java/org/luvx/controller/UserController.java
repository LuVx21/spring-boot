package org.luvx.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {
/*
        @GetMapping("/hello")
        public String hello() {
            return "Welcome to reactive world ~";
        }
*/
        @GetMapping("/hello")
        public Mono<String> hello() {
            return Mono.just("Welcome to reactive world ~");
        }
}
