package org.luvx.module.user.model;

import lombok.Data;

/**
 * @ClassName: org.luvx.module.user.model.UserModel
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/11 21:10
 */
@Data
public class UserModel {
    private Long    userId;
    private String  userName;
    private String  password;
    private Integer age;
    private int     currentPage;
    private int     size;
}
