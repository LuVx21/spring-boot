package org.luvx.boot.es.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.springframework.util.ReflectionUtils.*;

@Slf4j
public class EsQueryUtils {
    @SneakyThrows
    public static void printDsl(Class<?> clazz, Query nsQuery, ElasticsearchTemplate template) {
        String className = "org.springframework.data.elasticsearch.core.RequestFactory";
        // Method searchRequest = findMethod(Class.forName(className),
        //         "searchRequest", Query.class, Class.class, IndexCoordinates.class);
        // searchRequest.setAccessible(true);
        // Object o = invokeMethod(searchRequest, template.getRequestFactory(), nsQuery, clazz,
        //         template.getIndexCoordinatesFor(clazz));
        //
        // String className1 = "org.elasticsearch.action.search.SearchRequest";
        // Field source = findField(Class.forName(className1), "source");
        // source.setAccessible(true);
        // Object dsl = ReflectionUtils.getField(source, o);
        // log.info("dsl语句:\n{}", dsl);
    }
}
