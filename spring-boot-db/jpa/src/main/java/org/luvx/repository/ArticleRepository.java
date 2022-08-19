package org.luvx.repository;

import org.luvx.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: org.luvx.repository
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/21 19:42
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>
        , QuerydslPredicateExecutor<Article> {
}
