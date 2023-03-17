package org.luvx.boot.tools;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.dao.config.DaoConfig;
import org.luvx.boot.tools.runner.config.RunnerConfig;
import org.luvx.boot.tools.service.config.ServiceConfig;
import org.luvx.boot.tools.web.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Slf4j
@ComponentScan({
        "org.luvx.boot"
})
@Import({
        DaoConfig.class,
        RunnerConfig.class,
        WebConfig.class,
        ServiceConfig.class
})
@SpringBootApplication
public class ToolApp {
    public static void main(String[] args) {
        log.info("starting");
        SpringApplication.run(ToolApp.class, args);
        log.info("starting success");
    }
}
