package org.luvx.boot.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.luvx.websocket.pojo.Message;
import org.luvx.boot.websocket.websocket.SocketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Controller
public class IndexController3 {
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 广播发送消息，将消息发送到指定的目标地址
     * 将消息发送到 WebSocket 配置类中配置的代理中（/topic）进行消息转发
     *
     * @param message 消息
     */
    @MessageMapping("/2topic")
    public void sendMessage0(Message message) {
        log.info("收到消息(topic):{}", message.toString());
        template.convertAndSend(message.getDestination(), message);
    }

    @MessageMapping("/2user")
    public void sendMessage1(Message message) {
        log.info("收到消息(user):{}", message.toString());
        String token = message.getTo().getToken();
        WebSocketSession session = SocketManager.get(token);
        if (session != null) {
            template.convertAndSendToUser(token, message.getDestination(), message);
        }
    }
}