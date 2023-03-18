package org.luvx.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo(scanBasePackages = {"org.luvx.dubbo.service.impl"})
@SpringBootApplication
public class ProviderApplication {
    public static void main(String[] args) {
        new EmbeddedZooKeeper(2181, false).start();
        SpringApplication.run(ProviderApplication.class, args);
    }
}