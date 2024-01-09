package org.luvx.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * @author zhouli
 */
@SpringBootApplication
@EnableWebSocket
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
