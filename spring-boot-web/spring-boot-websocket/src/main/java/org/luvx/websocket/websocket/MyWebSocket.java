package org.luvx.websocket.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
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
    private static final Map<String, MyWebSocket> webSocketMap = new LinkedHashMap<>();
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
        log.info("新的连接加入：{}", session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到客户端{}消息：{}", session.getId(), message);
        try {
            sendMessage("收到消息：" + message);
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
        log.info("连接关闭:{}", session.getId());
    }

    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public static void broadcast() {
        webSocketMap.forEach((k, v) -> {
            try {
                v.sendMessage("这是一条来自服务端的测试广播");
            } catch (Exception ignored) {
            }
        });
    }
}