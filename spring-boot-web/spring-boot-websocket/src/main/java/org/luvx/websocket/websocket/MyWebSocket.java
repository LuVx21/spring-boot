package org.luvx.websocket.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 接受websocket请求路径
 *
 * @author Ren, Xie
 */
@Slf4j
@Component
@ServerEndpoint(value = "/websocket")
public class MyWebSocket {
    /**
     * 保存所有在线socket连接
     */
    private static final Map<String, MyWebSocket> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 记录当前在线数目
     */
    private static final AtomicInteger            cnt          = new AtomicInteger(0);

    /**
     * 当前连接
     * 每个websocket连入都会创建一个MyWebSocket实例
     */
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketMap.put(session.getId(), this);
        cnt.incrementAndGet();
        log.info("新的连接(session:{})加入(当前共{}个客户端)", session.getId(), webSocketMap.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到客户端{}消息：{}", session.getId(), message);
        try {
            broadcast(session.getId() + ":" + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable error, Session session) {
        log.info("发生错误{},{}", session.getId(), error.getMessage());
    }

    @OnClose
    public void onClose() {
        webSocketMap.remove(session.getId());
        cnt.decrementAndGet();
        log.info("连接关闭(session:{}),当前剩余{}个客户端", session.getId(), webSocketMap.size());
    }

    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public static void broadcast(String message) {
        webSocketMap.forEach((k, v) -> {
            try {
                v.sendMessage(message);
            } catch (Exception ignored) {
            }
        });
    }
}