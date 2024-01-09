package org.luvx.mapper.xml;

import org.apache.ibatis.annotations.Param;
import org.luvx.common.entity.Article;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface ArticleMapper {
    Article selectByPrimaryKey(@Param("id") Serializable id);

    List<Article> selectArticleByUserId(@Param("userId") Serializable id);
}

