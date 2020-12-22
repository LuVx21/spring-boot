package org.luvx.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.luvx.websocket.pojo.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ren, Xie
 */
@Slf4j
@RestController
public class IndexController2 {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message) {
        message.setTimestamp(System.currentTimeMillis());
        return message;
    }
}