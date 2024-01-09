package org.luvx.boot.tools.dao.entity;

import com.google.common.base.MoreObjects;
import io.mybatis.provider.Entity.Column;
import io.mybatis.provider.Entity.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
//@ToString
@Table("tvbox_live")
@NoArgsConstructor
public class TvboxLive {
    @Column(id = true)
    private long          id;
    private String        groupName;
    private String        name;
    private String        url;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public TvboxLive(String groupName, String name, String url) {
        this.groupName = groupName;
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("groupName", groupName)
                .add("name", name)
                .add("url", url)
                .toString();
    }
}
