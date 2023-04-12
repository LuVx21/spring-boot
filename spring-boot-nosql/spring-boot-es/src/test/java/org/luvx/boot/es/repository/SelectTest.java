package org.luvx.boot.es.repository;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ValueCountAggregationBuilder;
import org.junit.jupiter.api.Test;
import org.luvx.boot.es.EsAppTests;
import org.luvx.boot.es.entity.User;
import org.luvx.coding.common.more.MorePrints;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class SelectTest extends EsAppTests {

    @Test
    void m1() {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(User.ARTICLES_ID, 1);
        NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery(User.ARTICLES, termQueryBuilder, ScoreMode.None);
        BoolQueryBuilder bqBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery(User.USER_NAME, "xie_1"))
                .must(nestedQueryBuilder);

        List<User> list = userEsService.list(bqBuilder);

        System.out.println(JSON.toJSONString(list, JSONWriter.Feature.PrettyFormat));
    }

    /**
     * https://ost.51cto.com/posts/17785
     * 1. 嵌套对象内部求和(内部过滤后求和呢)
     * 2. 嵌套对象内部过滤,只返回过滤到的
     */
    @Test
    void m2() {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(User.ARTICLES_ID, 1);
        NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery(User.ARTICLES, termQueryBuilder, ScoreMode.None);
        BoolQueryBuilder bqBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery(User.USER_NAME, "xie_1"))
//                .must(nestedQueryBuilder)
                ;

        ValueCountAggregationBuilder countBuilder = AggregationBuilders.count("cnt").field(User.ID);
        SumAggregationBuilder sumBuilder1 = AggregationBuilders.sum("sum_age").field(User.AGE);
        SumAggregationBuilder sumBuilder2 = AggregationBuilders.sum("sum_id").field(User.ARTICLES_ID);
        // TermsAggregationBuilder field = AggregationBuilders.terms("sum_sales").field("goodsSales");

        NativeSearchQuery nsQuery = new NativeSearchQueryBuilder()
                .withQuery(bqBuilder)
                .withAggregations(countBuilder, sumBuilder1, sumBuilder2)
                .build();
//        log.info("dsl语句:\n{}", nsQuery.getQuery());
//        log.info("dsl聚合:\n{}", nsQuery.getAggregations());
        printDsl(User.class, nsQuery);


        SearchHits<User> tUser = template.search(nsQuery, User.class, IndexCoordinates.of(User.INDEX));

        Aggregations aggregations = (Aggregations) tUser.getAggregations().aggregations();
        ParsedValueCount cnt = aggregations.get("cnt");
        ParsedSum sumAge = aggregations.get("sum_age");
        ParsedSum sumId = aggregations.get("sum_id");

        MorePrints.println(
                cnt.getValue(),
                sumAge.getValue(),
                sumId.getValue()
        );
    }

    @SneakyThrows
    public void printDsl(Class<?> clazz, NativeSearchQuery nsQuery) {
        Method searchRequest = ReflectionUtils.findMethod(Class.forName("org.springframework.data.elasticsearch.core.RequestFactory"), "searchRequest", Query.class, Class.class, IndexCoordinates.class);
        searchRequest.setAccessible(true);
        Object o = ReflectionUtils.invokeMethod(searchRequest, template.getRequestFactory(), nsQuery, clazz, template.getIndexCoordinatesFor(clazz));

        Field source = ReflectionUtils.findField(Class.forName("org.elasticsearch.action.search.SearchRequest"), "source");
        source.setAccessible(true);
        Object s = ReflectionUtils.getField(source, o);
        log.info("dsl:{}", s);
    }
}