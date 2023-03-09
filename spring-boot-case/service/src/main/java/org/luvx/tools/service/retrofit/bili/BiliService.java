package org.luvx.tools.service.retrofit.bili;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import com.github.phantomthief.util.MoreFunctions;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.requireNonNullElse;

@Slf4j
@Service
public class BiliService {
    @Resource
    private BiliApi       api;
    @Resource
    private MongoTemplate mongoTemplate;

    private final String      tableName = "bili";
    private final Set<String> set       = Set.of(
            "pic", "title", "description",
            "author", "mid", "created",
            "length", "aid"
    );

    public List<String> upVideo(long upId) {
        final int limit = 30;
        final AtomicBoolean isEnd = new AtomicBoolean(false);
        AbstractIterator<JSONObject> iterator = new AbstractIterator<>() {
            private int pageNo = 1;

            @Nullable
            @Override
            protected JSONObject computeNext() {
                MoreFunctions.runCatching(() ->
                        TimeUnit.SECONDS.sleep(2)
                );
                return isEnd.get() ? endOfData() : api.getVideoList(upId, pageNo++, limit, "pubdate");
            }
        };

        Criteria criteria = Criteria.where("mid").is(upId);
        Query query = Query.query(criteria)
                .with(Sort.by(Direction.DESC, "created"));

        Map max = requireNonNullElse(mongoTemplate.findOne(query, Map.class, tableName), Collections.emptyMap());
        long createdLast = MapUtils.getLongValue(max, "created", 0L);

        List<String> result = Lists.newArrayList();
        iterator.forEachRemaining(response -> {
            List<Map<String, Object>> eval1 = (List<Map<String, Object>>) JSONPath.eval(response, "$.data.list.vlist");
            Preconditions.checkNotNull(eval1, "响应:%s", response);
            for (Map<String, Object> map : eval1) {
                long created = MapUtils.getLongValue(map, "created", -1L);
                if (created <= createdLast) {
                    isEnd.set(true);
                    return;
                }

                Map<String, Object> insert = map.entrySet().stream()
                        .filter(e -> set.contains(e.getKey()))
                        .filter(e -> Objects.nonNull(e.getValue()))
                        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
                String id = MapUtils.getString(map, "bvid");
                insert.put("_id", id);
                mongoTemplate.save(insert, tableName);
                result.add(id);
            }
            if (CollectionUtils.size(eval1) < limit) {
                isEnd.set(true);
                return;
            }
        });
        return result;
    }
}
