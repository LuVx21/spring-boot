package org.luvx.entity;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long articleId;
    private String articleName;
    private Date createTime;
}