package org.luvx.boot.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.websocket.service.SocketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController("/v1")
public class IndexController {
    @GetMapping("/broadcast")
    public void broadcast() {
        String message = "这是一条来自服务端的测试广播" + LocalDateTime.now();
        log.info("广播:{}", message);
        SocketService.broadcast(message);
    }
}