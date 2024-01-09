//package org.luvx.boot.es.repository;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.tuple.Pair;
//import org.apache.lucene.search.join.ScoreMode;
//import org.elasticsearch.index.query.*;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.Aggregations;
//import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
//import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
//import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
//import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
//import org.elasticsearch.search.aggregations.metrics.*;
//import org.elasticsearch.search.sort.SortOrder;
//import org.junit.jupiter.api.Test;
//import org.luvx.boot.es.EsAppTests;
//import org.luvx.boot.es.entity.User;
//import org.luvx.boot.es.utils.EsQueryUtils;
//import org.luvx.coding.common.more.MorePrints;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//
//import java.util.List;
//
//import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
//import static org.elasticsearch.index.query.QueryBuilders.termQuery;
//import static org.luvx.boot.es.entity.User.*;
//
//@Slf4j
//public class SelectTest extends EsAppTests {
//
//    @Test
//    void unNestedTest() {
//        BoolQueryBuilder bqBuilder = QueryBuilders.boolQuery()
//                // .must(termQuery(User.USER_NAME, "xie_1"))
//                .must(termQuery(UN_NESTED_ARTICLE + ARTICLE_ID, 1));
//
//        List<User> list = userEsService.list(bqBuilder, Pair.of(ID, SortOrder.ASC));
//        list.forEach(System.out::println);
//
//        bqBuilder = QueryBuilders.boolQuery()
//                // .must(termQuery(User.USER_NAME, "xie_1"))
//                .must(termQuery(UN_NESTED_ARTICLES + ARTICLE_ID, 1));
//
//        list = userEsService.list(bqBuilder, Pair.of(ID, SortOrder.ASC));
//        list.forEach(System.out::println);
//    }
//
//    @Test
//    void nestedTest() {
//        TermQueryBuilder tqBuilder = termQuery(NESTED_ARTICLE + ARTICLE_ID, 1);
//        NestedQueryBuilder nqBuilder = nestedQuery(NESTED_ARTICLE, tqBuilder, ScoreMode.None);
//        BoolQueryBuilder bqBuilder = QueryBuilders.boolQuery()
//                .must(termQuery(User.USER_NAME, "xie_1"))
//                .must(nqBuilder);
//
//        List<User> list = userEsService.list(bqBuilder, Pair.of(ID, SortOrder.ASC));
//        list.forEach(System.out::println);
//
//        // ------------------------------------------
//
//        String nestedArticlesId = NESTED_ARTICLES + ARTICLE_ID;
//        tqBuilder = termQuery(nestedArticlesId, 8);
//        nqBuilder = nestedQuery(NESTED_ARTICLES, tqBuilder, ScoreMode.None);
//        bqBuilder = QueryBuilders.boolQuery()
//                .must(termQuery(User.USER_NAME, "xie_8"))
//                .must(nqBuilder);
//
//        list = userEsService.list(bqBuilder, Pair.of(ID, SortOrder.ASC));
//        list.forEach(System.out::println);
//    }
//
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
//}