package org.luvx.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: org.luvx.entity
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/29 11:08
 */
@Data
public class Message {
    /**
     * id
     */
    private Long id;

    /**
     * 消息
     */
    private String msg;

    /**
     * 时间戳
     */
    private Date sendTime;

}