package org.luvx.boot.es.service;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import com.github.phantomthief.util.MoreFunctions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.luvx.boot.es.utils.EsQueryUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
public abstract class AbstractEsService<T> {
    @Resource
    private ElasticsearchTemplate template;

    private Class<T>         clazz;
    private IndexCoordinates indexCoordinates;

    public Class<T> entityClass() {
        if (clazz == null) {
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        }
        return clazz;
    }

    protected abstract String indexName();

    public IndexCoordinates index() {
        return indexCoordinates != null ? indexCoordinates : IndexCoordinates.of(indexName());
    }

    public void save(T obj) {
        template.save(obj, index());
    }

    public void save(Iterable<T> list) {
        template.save(list, index());
    }

    public void deleteById(String id) {
        deleteByIds(Collections.singletonList(id));
    }

    public void deleteByIds(Collection<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (String id : ids) {
            template.delete(id, index());
        }
    }

    public void updateById(@Nonnull String id, T obj) {
        BeanMap beanMap = new BeanMap(obj);
        Map<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(beanMap.size());
        for (Entry<Object, Object> entry : beanMap.entrySet()) {
            map.put(Objects.toString(entry.getKey()), entry.getValue());
        }

        UpdateQuery query = UpdateQuery.builder(id)
                .withDocument(Document.from(map))
                .build();
        template.update(query, index());
    }

    public long count(Query query) {
        return template.count(query, index());
    }

    public Optional<T> getOne(T obj) {
        Query query = genSearchQuery(obj, null, null);
        SearchHit<T> hit = template.searchOne(query, (Class<T>) obj.getClass(), index());
        return ofNullable(hit)
                .map(SearchHit::getContent);
    }

    @SafeVarargs
    public final List<T> list(BoolQuery.Builder query, @Nullable Pair<String, Sort.Direction>... sorts) {
        NativeQueryBuilder builder = new NativeQueryBuilder()
                .withQuery(query.build()._toQuery());
        if (sorts != null) {
            Arrays.stream(sorts)
                    .map(p -> Sort.by(p.getValue(), p.getKey()))
                    .forEachOrdered(builder::withSort);
        }
        NativeQuery nsQuery = builder.build();
        return listOriginal(nsQuery);
    }

    public SearchHits<T> list(Query query) {
        MoreFunctions.runCatching(() -> EsQueryUtils.printDsl(entityClass(), query, template));
        return template.search(query, entityClass(), index());
    }

    public List<T> listOriginal(Query query) {
        return list(query).stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public SearchHits<T> page(BoolQuery.Builder query, int current, int size, List<Order> orders) {
        NativeQueryBuilder builder = new NativeQueryBuilder()
                .withQuery(query.build()._toQuery())
                // .withHighlightQuery(highlightQuery)
                .withPageable(PageRequest.of(
                        current - 1, size, Sort.by(ListUtils.emptyIfNull(orders))
                ));
        return list(builder.build());
    }

    @SneakyThrows
    public NativeQuery genSearchQuery(T obj,
                                      Consumer<BoolQuery.Builder> boolQueryBuilderConsumer,
                                      Consumer<NativeQueryBuilder> searchBuilderConsumer
    ) {
        BoolQuery.Builder boolQueryBuilder = genBoolQuery(obj, boolQueryBuilderConsumer);

        NativeQueryBuilder builder = new NativeQueryBuilder()
                .withQuery(boolQueryBuilder.build()._toQuery());
        if (searchBuilderConsumer != null) {
            searchBuilderConsumer.accept(builder);
        }
        return builder.build();
    }

    private BoolQuery.Builder genBoolQuery(T obj, Consumer<BoolQuery.Builder> boolQueryBuilderConsumer) {
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        for (TermQuery.Builder termQueryBuilder : getMustQueryList(obj)) {
            boolQueryBuilder.must(termQueryBuilder.build()._toQuery());
        }
        if (boolQueryBuilderConsumer != null) {
            boolQueryBuilderConsumer.accept(boolQueryBuilder);
        }
        return boolQueryBuilder;
    }

    public List<TermQuery.Builder> getMustQueryList(T obj) {
        List<TermQuery.Builder> queryList = Lists.newArrayList();
        for (Field field : FieldUtils.getAllFields(obj.getClass())) {
            field.setAccessible(true);
            org.springframework.data.elasticsearch.annotations.Field esField =
                    field.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);
            if (esField == null) {
                continue;
            }
            String fieldName = StringUtils.firstNonEmpty(esField.name(), esField.value(), field.getName());
            if (StringUtils.isEmpty(fieldName)) {
                continue;
            }
            Object val = null;
            try {
                val = field.get(obj);
            } catch (Exception ignore) {
            }
            if (val == null) {
                continue;
            }
            TermQuery.Builder builder = QueryBuilders.term()
                    .field(fieldName).value(val.toString());
            queryList.add(builder);
        }
        return queryList;
    }
}
