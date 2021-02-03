package org.luvx.schedule.dynamic.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Ren, Xie
 */
@Getter
@Setter
public class TaskModel {
    private String id;
    private String cron;

    private String sql;
}
