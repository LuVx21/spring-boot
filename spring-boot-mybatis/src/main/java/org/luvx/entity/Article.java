package org.luvx.entity;

import lombok.*;
import org.luvx.common.BaseQueryEntity;

import java.io.Serializable;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Article extends BaseQueryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String articleName;
    private Date createTime;
    private Long userId;
}