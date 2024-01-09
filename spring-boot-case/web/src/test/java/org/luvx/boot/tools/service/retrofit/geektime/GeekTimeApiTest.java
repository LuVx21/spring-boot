package org.luvx.boot.tools.service.retrofit.geektime;

import java.util.Map;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.luvx.boot.tools.BaseAppTests;
import org.luvx.boot.tools.service.api.GeekTimeApi;
import org.luvx.boot.tools.service.api.GeekTimeApi.ArticleBody;
import org.luvx.boot.tools.service.api.GeekTimeApi.ArticlesBody;
import org.luvx.boot.tools.service.api.GeekTimeApi.IntroBody;

class GeekTimeApiTest extends BaseAppTests {
    @Resource
    private GeekTimeApi geekTimeApi;

    @Test
    void articles() {
        ArticlesBody body = new ArticlesBody();
        body.setCid(100119701);
        Map<String, Object> response = geekTimeApi.articles(body);
        System.out.println(response);
    }

    @Test
    void intro() {
        IntroBody body = new IntroBody();
        body.setCid("100119701");
        Map<String, Object> response = geekTimeApi.intro(body);
        System.out.println(response);
    }

    @Test
    void article() {
        ArticleBody body = new ArticleBody();
        body.setId("566666");
        String json = geekTimeApi.article(body);
        System.out.println(json);
    }
}