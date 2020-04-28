package org.luvx.module.domain;

import lombok.Data;
import org.luvx.common.base.BaseModel;
import org.luvx.module.domain.finder.UserFinder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ebean_user")
public class User extends BaseModel {
    public static final UserFinder find = new UserFinder();

    @Id
    private Long userId;
    private String userName;
    private String password;
    private Integer age;
}
