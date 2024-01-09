package org.luvx.boot.websocket.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SocketManager {
    private static final Map<String, WebSocketSession> webSocketMap = new ConcurrentHashMap<>();

    public static void add(String key, WebSocketSession webSocketSession) {
        webSocketMap.put(key, webSocketSession);
    }

    public static void remove(String key) {
        webSocketMap.remove(key);
    }

    public static WebSocketSession get(String key) {
        return webSocketMap.get(key);
    }
}
