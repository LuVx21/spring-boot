package org.luvx.boot.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 新启动一个服务
     */
    // @Bean
    // public TomcatServletWebServerFactory servletContainer() {
    //     return new TomcatServletWebServerFactory(8081);
    // }
}
