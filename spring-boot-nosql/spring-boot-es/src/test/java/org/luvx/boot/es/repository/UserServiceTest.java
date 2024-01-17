package org.luvx.boot.es.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.es.EsAppTests;
import org.luvx.boot.es.entity.User;
import org.luvx.coding.common.more.MorePrints;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightFieldParameters;

import java.util.List;

@Slf4j
public class UserServiceTest extends EsAppTests {

    @Test
    void m1() {
        User user = User.builder().id(80L).build();
        userEsService.save(user);

        // userEsService.getOne(user)
        //         .ifPresent(System.out::println);

        long count = userEsService.count(new NativeQueryBuilder().build());
        System.out.println(count);
    }

    @Test
    void m2() {
        BoolQuery.Builder builder = QueryBuilders.bool();
        builder.must(QueryBuilders.term().field("age").value(18).build()._toQuery());

        // List<User> list = userEsService.list(builder, Pair.of(User.ID, Sort.Direction.ASC));
        // System.out.println(list);

        SearchHits<User> hits = userEsService.page(builder, 1, 20, List.of(Order.desc(User.ID)));
        MorePrints.println(hits.getTotalHits(), hits);
    }

    @Test
    void testTemplate() {
        Criteria criteria = Criteria
                .where(new SimpleField(User.ID)).is(110L)
                .and(new SimpleField(User.USER_NAME)).contains("_9");

        HighlightField highlightField = new HighlightField(User.USER_NAME, HighlightFieldParameters.builder().withPreTags("<em>").withPostTags("</em>").build());

        HighlightQuery query1 = new HighlightQuery(new Highlight(Lists.newArrayList(highlightField)), User.class);

        CriteriaQuery query = new CriteriaQueryBuilder(criteria)
                .withHighlightQuery(query1)
                .build();

        userEsService.list(query).getSearchHits()
                .forEach(System.out::println);
    }
}