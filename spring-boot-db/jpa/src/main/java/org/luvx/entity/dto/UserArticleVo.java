package org.luvx.entity.dto;

import lombok.Data;

@Data
public class UserArticleVo {
    private Long   userId;
    private Long   articleId;
    private String userName;
    private String articleName;
}
