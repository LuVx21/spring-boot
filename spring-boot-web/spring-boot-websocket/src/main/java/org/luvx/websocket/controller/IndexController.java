package org.luvx.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.luvx.websocket.websocket.MyWebSocket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ren, Xie
 */
@Slf4j
@RestController("/v1")
public class IndexController {
    @GetMapping("/broadcast")
    public void broadcast() {
        log.info("rest 请求");
        MyWebSocket.broadcast("这是一条来自服务端的测试广播");
    }
}