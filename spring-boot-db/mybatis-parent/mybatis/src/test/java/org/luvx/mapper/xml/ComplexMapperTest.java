package org.luvx.mapper.xml;

import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.common.entity.Article;
import org.luvx.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ComplexMapperTest extends ApplicationTests {
    @Autowired
    UserMapper mapper;
    @Autowired
    ArticleMapper articleMapper;

    @Test
    void selectByPrimaryKey() {
        User user = mapper.selectByPrimaryKey(1L);
        System.out.println(user);
    }

    @Test
    void getArticleTest() {
        Article article = articleMapper.selectByPrimaryKey(1L);
        System.out.println(article);

        List<Article> list = articleMapper.selectArticleByUserId(1L);
        System.out.println(list);
    }
}