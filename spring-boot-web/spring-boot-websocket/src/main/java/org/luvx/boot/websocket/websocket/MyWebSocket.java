package org.luvx.boot.websocket.websocket;

import com.github.phantomthief.util.MoreFunctions;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.common.util.ApplicationContextUtils;
import org.luvx.boot.websocket.service.SocketService;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 接受websocket请求路径
 */
@Slf4j
@Component
@ServerEndpoint(value = "/websocket")
public class MyWebSocket {

    @Resource
    private SocketService service;

    /**
     * 当前连接
     * 每个websocket连入都会创建一个MyWebSocket实例
     */
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        SocketService.webSocketMap.put(session.getId(), this);
        SocketService.cnt.incrementAndGet();
        log.info("新的连接(session:{})加入(当前共{}个客户端)", session.getId(), SocketService.webSocketMap.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到消息,客户端:{} {}", session.getId(), message);
        SocketService.broadcast(session.getId() + ":" + message);
    }

    @OnError
    public void onError(Throwable error, Session session) {
        error.printStackTrace();
        log.info("发生错误{},{}", session.getId(), error.getMessage());
    }

    @OnClose
    public void onClose() {
        SocketService.webSocketMap.remove(session.getId());
        SocketService.cnt.decrementAndGet();
        log.info("连接关闭(session:{}),当前剩余{}个客户端", session.getId(), SocketService.webSocketMap.size());
    }

    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
}