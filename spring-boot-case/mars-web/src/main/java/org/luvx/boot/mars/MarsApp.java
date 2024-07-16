package org.luvx.boot.mars;

// import de.codecentric.boot.admin.server.config.EnableAdminServer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@ComponentScan({"org.luvx.boot"})
// @EnableAdminServer
// @EnableCdc
@SpringBootApplication
public class MarsApp {
    public static void main(String[] args) {
        log.info("starting");
        SpringApplication.run(MarsApp.class, args);
        log.info("(*^▽^*) 启动成功... (〃'▽'〃)");
    }
}
