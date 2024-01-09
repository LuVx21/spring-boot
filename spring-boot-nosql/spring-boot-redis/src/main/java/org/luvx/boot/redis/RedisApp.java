package org.luvx.boot.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableCaching
@SpringBootApplication
public class RedisApp {
    public static void main(String[] args) {
        SpringApplication.run(RedisApp.class, args);
    }
}
