package org.luvx.boot.oauth2.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResourceApp {
    public static void main(String[] args) {
        SpringApplication.run(ResourceApp.class, args);
    }

    /**
     * 新启动一个服务
     */
    // @Bean
    // public TomcatServletWebServerFactory servletContainer() {
    //     return new TomcatServletWebServerFactory(8081);
    // }
}
