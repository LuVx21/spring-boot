package org.luvx.boot.rpc.dubbo.service.impl;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo(scanBasePackages = {"org.luvx.boot.rpc.dubbo.service.impl"})
@SpringBootApplication
public class ProviderApplication {
    public static void main(String[] args) {
        new EmbeddedZooKeeper(2181, false).start();
        SpringApplication.run(ProviderApplication.class, args);
    }
}