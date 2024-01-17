package org.luvx.boot.es.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.luvx.boot.es.EsAppTests;
import org.luvx.boot.es.entity.User;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.nested;
import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.term;
import static org.luvx.boot.es.entity.User.*;
import static org.springframework.data.elasticsearch.client.elc.Queries.termQuery;

@Slf4j
public class SelectTest extends EsAppTests {

    @Test
    void unNestedTest() {
        BoolQuery.Builder bqBuilder = QueryBuilders.bool()
                // .must(termQuery(User.USER_NAME, "xie_1")._toQuery())
                .must(termQuery(UN_NESTED_ARTICLE + ARTICLE_ID, "1")._toQuery());

        List<User> list = userEsService.list(bqBuilder, Pair.of(ID, Direction.ASC));
        list.forEach(System.out::println);

        System.out.println("------------------------------------------");

        bqBuilder = QueryBuilders.bool()
                // .must(termQuery(User.USER_NAME, "xie_1")._toQuery())
                .must(termQuery(UN_NESTED_ARTICLES + ARTICLE_ID, "1")._toQuery());

        list = userEsService.list(bqBuilder, Pair.of(ID, Direction.ASC));
        list.forEach(System.out::println);
    }

    @Test
    void nestedTest() {
        TermQuery.Builder tqBuilder = term().field(NESTED_ARTICLE + ARTICLE_ID).value(1);
        NestedQuery.Builder nqBuilder = nested().path(NESTED_ARTICLE)
                .query(tqBuilder.build()._toQuery()).scoreMode(ChildScoreMode.None);
        BoolQuery.Builder bqBuilder = QueryBuilders.bool()
                // .must(termQuery(User.USER_NAME, "xie_1")._toQuery())
                .must(nqBuilder.build().query());

        List<User> list = userEsService.list(bqBuilder, Pair.of(ID, Direction.ASC));
        list.forEach(System.out::println);

        System.out.println("------------------------------------------");

        tqBuilder = term().field(NESTED_ARTICLES + ARTICLE_ID).value(1);
        nqBuilder = nested().path(NESTED_ARTICLES)
                .query(tqBuilder.build()._toQuery()).scoreMode(ChildScoreMode.None);
        bqBuilder = QueryBuilders.bool()
                // .must(termQuery(User.USER_NAME, "xie_1")._toQuery())
                .must(nqBuilder.build().query());

        list = userEsService.list(bqBuilder, Pair.of(ID, Direction.ASC));
        list.forEach(System.out::println);
    }

//    @Test
//    void m2() {
//        final String nestedArticlesId = NESTED_ARTICLES + ARTICLE_ID;
//        TermQueryBuilder tqBuilder = termQuery(nestedArticlesId, 8);
//        NestedQueryBuilder nqBuilder = nestedQuery(NESTED_ARTICLES, tqBuilder, ScoreMode.None);
//        BoolQueryBuilder bqBuilder = QueryBuilders.boolQuery()
//                .must(termQuery(User.USER_NAME, "xie_8"))
//                .must(nqBuilder);
//
//        // count
//        String asCnt = "cnt";
//        ValueCountAggregationBuilder countBuilder = AggregationBuilders.count(asCnt).field(User.ID);
//
//        // sum
//        String asSumAge = "sum_age";
//        SumAggregationBuilder sumBuilder = AggregationBuilders.sum(asSumAge).field(User.AGE);
//
//        // 嵌套 sum
//        String outSumId1 = "out_sum_id", midSumId2 = "mid_sum_id", innerSumId = "inner_sum_id";
//        SumAggregationBuilder innerSumBuilder = AggregationBuilders.sum(innerSumId).field(nestedArticlesId);
//        String outSumId1_1 = "out_sum_id_1";
//        NestedAggregationBuilder naBuilder = AggregationBuilders
//                .nested(outSumId1_1, NESTED_ARTICLES)
//                .subAggregation(innerSumBuilder);
//        // 嵌套过滤后 sum
//        FilterAggregationBuilder faBuilder = AggregationBuilders
//                .filter(midSumId2, QueryBuilders.boolQuery().must(tqBuilder))
//                .subAggregation(innerSumBuilder);
//        NestedAggregationBuilder naFilterBuilder = AggregationBuilders
//                .nested(outSumId1, NESTED_ARTICLES)
//                .subAggregation(faBuilder);
//
//        NativeSearchQuery nsQuery = new NativeSearchQueryBuilder()
//                .withQuery(bqBuilder)
//                .withAggregations(countBuilder, sumBuilder, naBuilder, naFilterBuilder)
//                .build();
//
//        EsQueryUtils.printDsl(userEsService.entityClass(), nsQuery, template);
//        SearchHits<User> response = template.search(nsQuery, userEsService.entityClass(), userEsService.index());
//
//        Aggregations aggregations = (Aggregations) response.getAggregations().aggregations();
//        ParsedValueCount cnt = aggregations.get(asCnt);
//        ParsedSum sumAge = aggregations.get(asSumAge);
//
//        ParsedNested aggregation = aggregations.get(outSumId1_1);
//        ParsedSum sumInner = aggregation.getAggregations().get(innerSumId);
//
//        ParsedNested outSumId = aggregations.get(outSumId1);
//        ParsedFilter midSumId = outSumId.getAggregations().get(midSumId2);
//        ParsedSum sumFilterInner = midSumId.getAggregations().get(innerSumId);
//
//        MorePrints.println(
//                cnt.getValue(),
//                sumAge.getValue(),
//                sumInner.getValue(),
//                sumFilterInner.getValue()
//        );
//    }
}