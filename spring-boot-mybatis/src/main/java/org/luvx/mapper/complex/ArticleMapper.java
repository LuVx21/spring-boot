package org.luvx.mapper.complex;

import org.apache.ibatis.annotations.Param;
import org.luvx.entity.Article;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: org.luvx.mapper.complex
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/12 19:50
 */
@Repository
public interface ArticleMapper {
    Article selectByPrimaryKey(@Param("id") Serializable id);

    List<Article> selectArticleByUserId(@Param("userId") Serializable id);
}

