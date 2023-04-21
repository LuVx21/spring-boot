package org.luvx.websocket.pojo;

import lombok.Data;


@Data
public class Message {
    /**
     * 消息
     */
    private String content;
    /**
     * 订阅地址
     */
    private String destination;
    private User   from;
    private User   to;
    private Long   timestamp;
}