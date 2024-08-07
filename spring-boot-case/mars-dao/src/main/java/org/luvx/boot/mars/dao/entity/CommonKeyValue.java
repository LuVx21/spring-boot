package org.luvx.boot.mars.dao.entity;

import io.mybatis.provider.Entity.Column;
import io.mybatis.provider.Entity.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table("common_key_value")
public class CommonKeyValue {
    @Column(id = true)
    private Long   id;
    private int    bizType;
    private String commonKey;
    private String commonValue;
    private int    invalid;
    private Long   createTime;
    private Long   updateTime;
}
