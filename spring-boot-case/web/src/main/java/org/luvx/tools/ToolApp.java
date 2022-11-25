package org.luvx.tools;

import com.github.lianjiatech.retrofit.spring.boot.config.RetrofitAutoConfiguration;

import lombok.extern.slf4j.Slf4j;
import org.luvx.tools.dao.config.DaoConfig;
import org.luvx.tools.runner.config.RunnerConfig;
import org.luvx.tools.service.config.ServiceConfig;
import org.luvx.tools.web.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Slf4j
@ComponentScan({
        "org.luvx.boot"
        // "org.luvx.tools"
})
@Import({
        DaoConfig.class,
        RunnerConfig.class,
        WebConfig.class,
        ServiceConfig.class,
        RetrofitAutoConfiguration.class
})
@SpringBootApplication
public class ToolApp {
    public static void main(String[] args) {
        log.info("starting");
        SpringApplication.run(ToolApp.class, args);
        log.info("starting success");
    }
}
