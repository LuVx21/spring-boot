package org.luvx.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.luvx.websocket.websocket.SocketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * @author Ren, Xie
 */
@Slf4j
@RestController
public class IndexController1 {
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 客户端发消息，服务端接收
     *
     * @param message
     */
    @MessageMapping("/sendServer")
    public void sendServer(String message) {
        log.info("服务端接收到消息:{}", message);
    }

    /**
     * 广播，服务器主动推给连接的客户端
     */
    @MessageMapping("/sendTopic")
    public void sendTopic(String message) {
        log.info("接收到消息:{}", message);
        template.convertAndSend("/topic/sendTopic", message);
    }

    /**
     * 客户端发消息，大家都接收，相当于直播说话
     *
     * @param message
     * @return
     */
    @SendTo("/topic/sendTopic")
    @MessageMapping("/sendAllUser")
    public String sendAllUser(String message) {
        log.info("接收到消息(注解):{}", message);
        return message;
    }

    /**
     * @param token
     */
    @MessageMapping("/sendUser")
    public void sendUser(String token) {
        log.info("token:{}", token);
        WebSocketSession session = SocketManager.get(token);
        if (session != null) {
            template.convertAndSendToUser(token, "/queue/sendUser", "您好" + token);
        }
    }

    /**
     * 点对点用户聊天，这边需要注意，由于前端传过来json数据，所以使用@RequestBody
     * 这边需要前端开通 var socket = new SockJS(host+'/chat' + '?token=4567');   token为指定name
     *
     * @param map
     */
    @MessageMapping("/sendMyUser")
    public void sendMyUser(@RequestBody Map<String, String> map) {
        final String token = map.get("token"), message = map.get("message");
        log.info("接收到来自(token:{})的消息:{}", token, message);
        WebSocketSession session = SocketManager.get(token);
        if (session != null) {
            log.info("sessionId:{}", session.getId());
            template.convertAndSendToUser(token, "/queue/sendUser", message);
        }
    }
}