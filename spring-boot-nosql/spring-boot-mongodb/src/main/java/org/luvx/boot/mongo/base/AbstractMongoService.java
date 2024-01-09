package org.luvx.boot.mongo.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractMongoService<T> {
    @Resource
    private MongoTemplate mongoTemplate;

    private final Class<T> entityClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    public List<T> save(Collection<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream()
                .map(mongoTemplate::save)
                .collect(Collectors.toList());
    }

    public void update(Criteria criteria, Update update) {
        mongoTemplate.updateMulti(Query.query(criteria), update, entityClass());
    }

    /**
     * // MongoJsonSchema jsonSchema = MongoJsonSchema.builder()
     * //         .build();
     * // Criteria criteria = Criteria.matchingDocumentStructure(jsonSchema);
     */
    public List<T> list(Criteria criteria) {
        return mongoTemplate.find(Query.query(criteria), entityClass());
    }

    public List<T> page(Criteria criteria, List<Order> orders, int pageNo, int pageSize) {
        orders = ObjectUtils.defaultIfNull(orders, Collections.emptyList());
        Query query = Query.query(criteria)
                .with(Sort.by(orders))
                .skip((long) (pageNo - 1) * pageSize)
                .limit(pageSize);
        return mongoTemplate.find(query, entityClass());
    }

    public void deleteByQuery(Criteria criteria) {
        mongoTemplate.remove(Query.query(criteria), entityClass());
    }

    public void deleteByIds(Collection<Serializable> ids) {
        ids.stream()
                .map(id -> Criteria.where("_id").is(id))
                .forEachOrdered(this::deleteByQuery);
    }

    public long count(Criteria criteria) {
        return mongoTemplate.count(Query.query(criteria), entityClass());
    }
}
