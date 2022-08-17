package org.luvx.tools.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

// @EnableSwagger2WebMvc
@EnableScheduling
@ComponentScan({
        "org.luvx.tools",
        "org.luvx.boot"
})
@SpringBootApplication
public class ToolApp {
    public static void main(String[] args) {
        SpringApplication.run(ToolApp.class, args);
    }
}
