package org.luvx.app.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Entity
@Data
public class Task {
    @Id
    private Long   id;
    private String cron;
    private String jobKey;
    private String jobGroup;
    private String descr;
}
