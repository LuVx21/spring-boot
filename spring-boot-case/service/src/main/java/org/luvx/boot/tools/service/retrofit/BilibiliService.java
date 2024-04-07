package org.luvx.boot.tools.service.retrofit;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.luvx.boot.tools.service.api.BilibiliApi;
import org.luvx.boot.tools.service.commonkv.CommonKeyValueService;
import org.luvx.boot.tools.service.commonkv.constant.CommonKVBizType;
import org.luvx.coding.common.cursor.CursorIteratorEx;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.commons.lang3.math.NumberUtils.toLong;
import static org.luvx.coding.common.util.DateUtils.timestampToDateTime;

@Slf4j
@Service
public class BilibiliService {
    private static final String TABLE_NAME_VIDEO = "bili_video";

    @Resource
    private BilibiliApi           api;
    @Resource
    private MongoTemplate         mongoTemplate;
    @Resource
    private CommonKeyValueService keyValueService;

    private final Set<String> keys = Set.of("_id", "title", "pubtime", "bvid", "upper");

    public void pullAll() {
        Map<String, Boolean> biliSeason = keyValueService.<Map<String, Boolean>>getData(CommonKVBizType.MAP, "bili_season")
                .orElse(Collections.emptyMap());
        for (Map.Entry<String, Boolean> entry : biliSeason.entrySet()) {
            if (!Boolean.TRUE.equals(entry.getValue())) {
                continue;
            }
            List<String> bvIds = pullSeasonList(toLong(entry.getKey()));
            log.info("bvids: {}", bvIds);
        }
    }

    public List<String> pullSeasonList(long seasonId) {
        AtomicInteger cursor = new AtomicInteger(1);
        int limit = 20;

        CursorIteratorEx<JSONObject, Integer, JSONObject> cursorIterator = CursorIteratorEx.<JSONObject, Integer, JSONObject>builder()
                .withInitCursor(cursor.get())
                .firstCursorCheckEnd(true)
                .withEndChecker(c -> c == null || c < 1)
                .withDataAccessor(c -> {
                    String json = api.seasonList(seasonId, cursor.get(), limit);
                    return JSON.parseObject(json);
                })
                .withDataExtractor(root -> {
                    return Optional.ofNullable(root.getJSONObject("data"))
                            .map(data -> data.getJSONArray("medias"))
                            .map(ArrayList::iterator)
                            .map(it -> Iterators.transform(it, e -> (JSONObject) e))
                            .orElse(Collections.emptyIterator());
                })
                .withCursorExtractor((JSONObject root) -> {
                    JSONObject data = root.getJSONObject("data");
                    // 库中没有数据
                    if (CollectionUtils.size(data.getJSONArray("medias")) < limit) {
                        return null;
                    }
                    // 接口有问题,没有分页取
                    // return cursor.incrementAndGet();
                    return null;
                }).build();

        List<String> result = Lists.newArrayList();
        cursorIterator.stream().forEachOrdered(media -> {
            final Long id = media.getLong("id");
            JSONObject o = mongoTemplate.findById(id, JSONObject.class, TABLE_NAME_VIDEO);
            if (o != null) {
                return;
            }

            media.put("_id", id);
            media.computeIfPresent("pubtime", (k, v) -> timestampToDateTime(toLong(v.toString()) * 1000));
            media.entrySet().removeIf(e -> !keys.contains(e.getKey()));
            JSONObject upper = media.getJSONObject("upper");
            upper.put("seasonId", seasonId);
            media.put("invalid", 0);
            mongoTemplate.save(media, TABLE_NAME_VIDEO);
            log.debug("{}", media);
            result.add(media.getString("bvid"));
        });
        return result;
    }
}
