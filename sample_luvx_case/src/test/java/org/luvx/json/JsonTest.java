package org.luvx.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;
import org.luvx.entity.Article;
import org.luvx.entity.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTest {

    private String json = "{\"age\":26,\"articles\":[{\"articleName\":\"test1\",\"createTime\":1544779082310},{\"articleName\":\"test2\",\"createTime\":1544779082311}],\"password\":\"1234\",\"userId\":999,\"userName\":\"Luvx\"}";

    @Test
    public void toStr() {
        User user = User.builder().userName("Luvx").password("1234").age(26).build();;
        user.setId(999L);
        List<Article> articles = new ArrayList<>();

        Article article = Article.builder().articleName("test1").createTime(new Date(System.currentTimeMillis())).build();
        Article article1 = Article.builder().articleName("test2").createTime(new Date(System.currentTimeMillis() + 1)).build();
        articles.add(article);
        articles.add(article1);
        user.setArticles(articles);
        String result = JSON.toJSONString(user);
        System.out.println(result);
    }

    @Test
    public void toObj() {
        // 解析整个json
        User user = JSON.parseObject(json, User.class);
        System.out.println(user);
    }

    @Test
    public void arrayTest() {
        // 解析为json对象
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray articles = jsonObject.getJSONArray("articles");
        // 方式1
        List<Article> list = JSON.parseObject(articles.toJSONString(), new TypeReference<List<Article>>() {
        });
        // 方式2
        List<Article> newList = JSON.parseObject(articles.toJSONString(), ArrayList.class);
        // 方式3
        List<Article> newNewList = JSONObject.parseArray(articles.toJSONString(), Article.class);
    }

    @Test
    public void mapTest() {
        Map<String, String> map = new HashMap<>();
        map.put("Luvx", "1234");
        map.put("foo", "bar");
        String json = JSON.toJSONString(map);
        System.out.println(json);

        Map<String, String> map1 = JSON.parseObject(json, HashMap.class);
        System.out.println(map1);
    }
}