package org.luvx.boot.es.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Article {
    private long          id;
    private String        articleName;
    private String        remark;

    public static Article of(long id, String articleName, String remark) {
        Article article = new Article();
        article.setId(id);
        article.setArticleName(articleName);
        article.setRemark(remark);
        return article;
    }
}