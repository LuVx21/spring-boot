package org.luvx.tools.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
// @EnableSwagger2WebMvc
@EnableScheduling
@ComponentScan({
        "org.luvx.tools",
        "org.luvx.boot"
})
@SpringBootApplication
public class ToolApp {
    public static void main(String[] args) {
        log.info("starting");
        SpringApplication.run(ToolApp.class, args);
        log.info("starting success");
    }
}
