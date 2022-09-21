package org.luvx.tools.dao.commonkv;

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
    private String key;
    private String value;
    private long   createTime;
    private long   updateTime;
}
