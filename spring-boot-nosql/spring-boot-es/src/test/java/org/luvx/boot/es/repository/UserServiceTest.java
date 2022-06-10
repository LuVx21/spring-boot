package org.luvx.boot.es.repository;

import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.luvx.boot.es.ApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.SimpleField;

public class UserServiceTest extends ApplicationTests {

    @Autowired
    private ElasticsearchRestTemplate template;

    @Test
    public void testTemplate() {
        Criteria criteria = Criteria
                .where(new SimpleField("id")).is(1L)
                .and(new SimpleField("userName")).contains("foo");

        CriteriaQuery query = new CriteriaQuery(criteria);
        HighlightBuilder blogTitle = SearchSourceBuilder.highlight().field("password");
        // HighlightQuery query1 = new HighlightQuery(blogTitle.build(), User.class);
        // query.setHighlightQuery(query1);
        //
        // template.search(query, User.class).getSearchHits()
        //         .forEach(System.out::println);
    }
}