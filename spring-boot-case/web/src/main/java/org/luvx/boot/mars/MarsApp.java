package org.luvx.boot.mars;

// import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mars.dao.config.DaoConfig;
import org.luvx.boot.mars.runner.config.RunnerConfig;
import org.luvx.boot.mars.service.config.ServiceConfig;
import org.luvx.boot.mars.web.config.WebConfig;
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
