package org.luvx;

import lombok.extern.slf4j.Slf4j;
import org.luvx.common.utils.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;

/**
 * @ClassName: org.luvx
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/8 16:18
 */
@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        applicationInfo();
    }

    private static void applicationInfo() {
        ServerProperties serverProperties = SpringContextHolder.getBean(ServerProperties.class);
        DataSourceProperties dataSourceProperties = SpringContextHolder.getBean(DataSourceProperties.class);

        log.info("服务运行在{}", serverProperties.getPort());
        log.info("数据库服务{}", dataSourceProperties.getUrl());
    }
}
