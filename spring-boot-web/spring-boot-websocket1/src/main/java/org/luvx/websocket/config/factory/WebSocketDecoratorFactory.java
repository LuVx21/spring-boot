package org.luvx.websocket.config.factory;

import lombok.extern.slf4j.Slf4j;
import org.luvx.websocket.websocket.SocketManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import java.security.Principal;

/**
 * @author Ren, Xie
 */
@Slf4j
@Component
public class WebSocketDecoratorFactory implements WebSocketHandlerDecoratorFactory {

    @Override
    public WebSocketHandler decorate(WebSocketHandler handler) {
        return new WebSocketHandlerDecorator(handler) {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                Principal principal = session.getPrincipal();
                String name = null;
                if (principal != null) {
                    name = principal.getName();
                    SocketManager.add(name, session);
                }
                super.afterConnectionEstablished(session);
                log.info("连接加入 sessionId:{}, token:{}", session.getId(), name != null ? name : "null");
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                Principal principal = session.getPrincipal();
                String name = null;
                if (principal != null) {
                    name = principal.getName();
                    SocketManager.remove(name);
                }
                super.afterConnectionClosed(session, closeStatus);
                log.info("连接断开 sessionId:{}, token:{}", session.getId(), name != null ? name : "null");
            }
        };
    }
}