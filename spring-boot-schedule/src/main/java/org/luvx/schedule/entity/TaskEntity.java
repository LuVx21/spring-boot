package org.luvx.schedule.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ren, Xie
 */
@Getter
@Setter
@ToString
public class TaskEntity {
    private String id;
    private String cron;
    private String sql;
    private String status;
}
