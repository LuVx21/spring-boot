package org.luvx.boot.tools.service.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.luvx.boot.tools.service.jsoup.SpiderParam.QueryRule;

import java.io.File;
import java.io.IOException;
import java.util.List;

class PageContentSpiderTest {
    @Test
    void m1() throws IOException {
        // String json = """
        //         """;
        // SpiderParam param = JSON.parseObject(json, SpiderParam.class);

        SpiderParam param = new SpiderParam()
                .setCountInPage(1)
                .setPageCount(2)
                .setIndexItemListRule("div.elementor-posts-container article h3.elementor-post__title a")
                .setIndexItemListPostProcessor(List::reversed)
                .setIndexItemTitleRule(QueryRule.of("", "text"))
                .setIndexItemUrlRule(QueryRule.of("", "href"))
                .setIndexNextPageUrlRule(QueryRule.of("nav.elementor-pagination a.prev", "href"))
                // .setIndexNextPageUrlPostProcessor()
                .setContentRule(QueryRule.of("div.elementor-element-cad010d div.elementor-widget-container > p > img.aligncenter", "data-src"))
                // .setContentPostProcessor()
                // .setContentNextPageUrlRule(QueryRule.of("div#novelcontent ul.novelbutton a#pb_next", "href"))
                // .setContentNextPageUrlPostProcessor()
                ;

        List<PageContent> list = param.newSpider().visit("");
        System.out.println(list);
    }

    @Test
    void m2() throws Exception {
    }

    @Test
    void m3() throws IOException {
        String path = "";
        String cssQuery = "";

        Document doc = Jsoup.parse(new File(path));
        Elements e = doc.select(cssQuery);
        System.out.println(e.size());
        System.out.println(e.first());
        // System.out.println(e.select());
    }
}