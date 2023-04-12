package org.luvx.boot.es.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.luvx.boot.es.EsAppTests;
import org.luvx.boot.es.entity.User;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.List;

@Slf4j
public class UserServiceTest extends EsAppTests {

    @Test
    void m1() {
        User user = User.builder().id(80L).build();
        userEsService.save(user);

        // userEsService.getOne(user)
        //         .ifPresent(System.out::println);

        long count = userEsService.count(new NativeSearchQueryBuilder().build());
        System.out.println(count);
    }

    @Test
    void m2() {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.termQuery("age", "18"));

        List<User> list = userEsService.list(builder, Pair.of("id", SortOrder.ASC));
        System.out.println(list);

        List<User> page = userEsService.page(builder, 1, 20, List.of(Order.desc("id")));
        System.out.println(page);
    }

    @Test
    void testTemplate() {
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