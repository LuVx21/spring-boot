package org.luvx.boot.tools.dao.entity;

import io.mybatis.provider.Entity.Column;
import io.mybatis.provider.Entity.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Table("oss_file")
public class OssFile {
    @Column(id = true)
    private long   id;
    private String scope;
    private String file;
    private long   visitCount;
    @Column(updatable = false)
    private String extData = "\"\"";

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
