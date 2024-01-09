//package org.luvx.boot.es.service;
//
//import com.github.phantomthief.util.MoreFunctions;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.beanutils.BeanMap;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.collections4.ListUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.reflect.FieldUtils;
//import org.apache.commons.lang3.tuple.Pair;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.TermQueryBuilder;
//import org.elasticsearch.search.sort.SortBuilders;
//import org.elasticsearch.search.sort.SortOrder;
//import org.luvx.boot.es.utils.EsQueryUtils;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Sort.Order;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.core.SearchHit;
//import org.springframework.data.elasticsearch.core.document.Document;
//import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.UpdateQuery;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import javax.annotation.Resource;
//import java.lang.reflect.Field;
//import java.lang.reflect.ParameterizedType;
//import java.util.*;
//import java.util.Map.Entry;
//import java.util.function.Consumer;
//import java.util.stream.Collectors;
//
//import static java.util.Optional.ofNullable;
//
//@Slf4j
//public abstract class AbstractEsService<T> {
//    @Resource
//    private ElasticsearchRestTemplate template;
//
//    private Class<T>         clazz;
//    private IndexCoordinates indexCoordinates;
//
//    public Class<T> entityClass() {
//        if (clazz == null) {
//            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
//            clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
//        }
//        return clazz;
//    }
//
//    protected abstract String indexName();
//
//    public IndexCoordinates index() {
//        return indexCoordinates != null ? indexCoordinates : IndexCoordinates.of(indexName());
//    }
//
//    public void save(T obj) {
//        template.save(obj, index());
//    }
//
//    public void save(Iterable<T> list) {
//        template.save(list, index());
//    }
//
//    public void deleteById(String id) {
//        deleteByIds(Collections.singletonList(id));
//    }
//
//    public void deleteByIds(Collection<String> ids) {
//        if (CollectionUtils.isEmpty(ids)) {
//            return;
//        }
//        for (String id : ids) {
//            template.delete(id, index());
//        }
//    }
//
//    public void updateById(@Nonnull String id, T obj) {
//        BeanMap beanMap = new BeanMap(obj);
//        Map<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(beanMap.size());
//        for (Entry<Object, Object> entry : beanMap.entrySet()) {
//            map.put(Objects.toString(entry.getKey()), entry.getValue());
//        }
//
//        UpdateQuery query = UpdateQuery.builder(id)
//                .withDocument(Document.from(map))
//                .build();
//        template.update(query, index());
//    }
//
//    public long count(NativeSearchQuery query) {
//        return template.count(query, index());
//    }
//
//    public Optional<T> getOne(T obj) {
//        NativeSearchQuery query = genSearchQuery(obj, null, null);
//        SearchHit<T> hit = template.searchOne(query, (Class<T>) obj.getClass(), index());
//        return ofNullable(hit)
//                .map(SearchHit::getContent);
//    }
//
//    @SafeVarargs
//    public final List<T> list(BoolQueryBuilder query, @Nullable Pair<String, SortOrder>... sorts) {
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder()
//                .withQuery(query);
//        if (sorts != null) {
//            Arrays.stream(sorts)
//                    .map(p -> SortBuilders.fieldSort(p.getKey()).order(p.getValue()))
//                    .forEachOrdered(builder::withSorts);
//        }
//        NativeSearchQuery nsQuery = builder.build();
//        return list(nsQuery);
//    }
//
//    public List<T> list(NativeSearchQuery query) {
//        MoreFunctions.runCatching(() -> EsQueryUtils.printDsl(entityClass(), query, template));
//        return template.search(query, entityClass(), index())
//                .stream()
//                .map(SearchHit::getContent)
//                .collect(Collectors.toList());
//    }
//
//    public List<T> page(BoolQueryBuilder query, int current, int size, List<Order> orders) {
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder()
//                .withQuery(query)
//                .withPageable(PageRequest.of(
//                        current - 1, size, Sort.by(ListUtils.emptyIfNull(orders))
//                ));
//        return list(builder.build());
//    }
//
//    @SneakyThrows
//    public NativeSearchQuery genSearchQuery(T obj,
//            Consumer<BoolQueryBuilder> boolQueryBuilderConsumer,
//            Consumer<NativeSearchQueryBuilder> searchBuilderConsumer
//    ) {
//        BoolQueryBuilder boolQueryBuilder = genBoolQuery(obj, boolQueryBuilderConsumer);
//
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder()
//                .withQuery(boolQueryBuilder);
//        if (searchBuilderConsumer != null) {
//            searchBuilderConsumer.accept(builder);
//        }
//        return builder.build();
//    }
//
//    private BoolQueryBuilder genBoolQuery(T obj, Consumer<BoolQueryBuilder> boolQueryBuilderConsumer) {
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        for (TermQueryBuilder termQueryBuilder : getMustQueryList(obj)) {
//            boolQueryBuilder.must(termQueryBuilder);
//        }
//        if (boolQueryBuilderConsumer != null) {
//            boolQueryBuilderConsumer.accept(boolQueryBuilder);
//        }
//        return boolQueryBuilder;
//    }
//
//    public List<TermQueryBuilder> getMustQueryList(T obj) {
//        List<TermQueryBuilder> queryList = Lists.newArrayList();
//        for (Field field : FieldUtils.getAllFields(obj.getClass())) {
//            field.setAccessible(true);
//            org.springframework.data.elasticsearch.annotations.Field esField =
//                    field.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);
//            if (esField == null) {
//                continue;
//            }
//            String fieldName = StringUtils.firstNonEmpty(esField.name(), esField.value(), field.getName());
//            if (StringUtils.isEmpty(fieldName)) {
//                continue;
//            }
//            Object val = null;
//            try {
//                val = field.get(obj);
//            } catch (Exception ignore) {
//            }
//            if (val == null) {
//                continue;
//            }
//            queryList.add(QueryBuilders.termQuery(fieldName, val));
//        }
//        return queryList;
//    }
//}
