package org.luvx.boot.oauth.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SsoApp {
    /**
     * 启动时配置 profile: sso1/sso2, 模拟多个客户端
     */
    public static void main(String[] args) {
        SpringApplication.run(SsoApp.class, args);
    }
}
