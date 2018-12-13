package org.luvx.entity;

import org.luvx.common.BaseQueryEntity;

import java.io.Serializable;
import java.sql.Date;

public class Article extends BaseQueryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long articleId;
    private String articleName;
    private Date createTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", articleName='" + articleName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}