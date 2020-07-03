package org.luvx.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @package: org.luvx
 * @author: Ren, Xie
 * @desc:
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long    id;
    private String  userName;
    private String  password;
    private Integer age;
}