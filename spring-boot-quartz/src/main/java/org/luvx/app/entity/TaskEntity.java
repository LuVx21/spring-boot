package org.luvx.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;
    private String  cron;
    private String  jobKey;
    private String  jobGroup;
    private String  clazz;
    private String  method;
    /**
     * 自动开启
     */
    private Boolean isAuto;
    /**
     * 逻辑删除
     */
    private Boolean isDeleted;
    private String  descr;
}
