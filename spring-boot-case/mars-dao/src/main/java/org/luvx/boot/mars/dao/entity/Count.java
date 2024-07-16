package org.luvx.boot.mars.dao.entity;

import io.mybatis.provider.Entity.Column;
import io.mybatis.provider.Entity.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table("count")
public class Count {
    @Column(id = true)
    private long countId;
    private int  countType;
    private int  countValue;
    private long updateTime;

}
