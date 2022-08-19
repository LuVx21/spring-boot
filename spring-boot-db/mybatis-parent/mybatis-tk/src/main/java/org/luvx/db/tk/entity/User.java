package org.luvx.db.tk.entity;

import io.mybatis.provider.Entity.Column;
import io.mybatis.provider.Entity.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import java.time.LocalDateTime;

@Table("user")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class User {
    @Column(id = true)
    private Long          id;
    // @Column("user_name")
    private String        userName;
    private String        password;
    private Integer       age;
    // @Column("update_time")
    private LocalDateTime updateTime;

    @Tolerate
    public User() {
    }
}