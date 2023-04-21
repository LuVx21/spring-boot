package org.luvx.boot.websocket.service;

import com.github.phantomthief.util.MoreFunctions;
import org.luvx.boot.websocket.websocket.MyWebSocket;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SocketService {
    /**
     * 保存所有在线socket连接
     */
    public static final Map<String, MyWebSocket> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 记录当前在线数目
     */
    public static final AtomicInteger            cnt          = new AtomicInteger(0);

    public static void broadcast(String message) {
        webSocketMap.forEach((k, v) ->
                MoreFunctions.runCatching(() -> v.sendMessage(message))
        );
    }
}
