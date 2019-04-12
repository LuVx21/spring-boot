package org.luvx.mapper.complex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.entity.Article;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName: org.luvx.mapper.complex
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/12 19:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ComplexMapperTest {

    @Autowired
    UserMapper    mapper;
    @Autowired
    ArticleMapper articleMapper;

    @Test
    public void selectByPrimaryKey() {
        User user = mapper.selectByPrimaryKey(1L);
        System.out.println(user);
    }

    @Test
    public void getArticleTest() {
        Article article = articleMapper.selectByPrimaryKey(1L);
        System.out.println(article);

        List<Article> list = articleMapper.selectArticleByUserId(10043L);
        System.out.println(list);
    }
}