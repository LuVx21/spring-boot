package org.luvx.boot.tools.service.rss;

import com.alibaba.fastjson2.JSONObject;
import com.github.phantomthief.util.MoreFunctions;
import com.mongodb.client.result.UpdateResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.luvx.boot.tools.common.CCC;
import org.luvx.boot.tools.dao.entity.CommonKeyValue;
import org.luvx.boot.tools.dao.mapper.CommonKeyValueMapper;
import org.luvx.boot.tools.dao.mongo.rss.PageContent;
import org.luvx.boot.tools.service.commonkv.CommonKeyValueService;
import org.luvx.boot.tools.service.commonkv.constant.CommonKVBizType;
import org.luvx.boot.tools.service.jsoup.SpiderParam;
import org.luvx.coding.common.more.MoreRuns;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RssService {
    private static final String TABLE_NAME_FEED = "rss_feed";

    private final String columnName = "spider_key";

    @Resource
    private CommonKeyValueService keyValueService;
    @Resource
    private CommonKeyValueMapper  keyValueMapper;
    @Resource
    private MongoTemplate         mongoTemplate;

    public String rss(String spiderKey) {
        Criteria criteria = Criteria.where(columnName).is(spiderKey)
                .and("invalid").is(0);
        Query query = Query.query(criteria)
                .with(Sort.by(Sort.Direction.DESC, "pubDate"))
                .with(Sort.by(Sort.Direction.DESC, "_id"))
                .limit(30);
        // 日期和map不匹配
        query.fields().exclude("createTime");
        List<JSONObject> list = MoreRuns.runWithTime(() -> mongoTemplate.find(query, JSONObject.class, TABLE_NAME_FEED));
        String domain = CCC.exposeDomain();
        String s = list.stream()
                .map(jo -> img2XmlItem(jo, domain))
                .collect(Collectors.joining());
        return STR."""
                <?xml version="1.0" encoding="UTF-8"?>
                <rss xmlns:atom="http://www.w3.org/2005/Atom" version="2.0">
                    <channel>
                        <title><![CDATA[美图-\{spiderKey}]]></title>

                \{s}
                    </channel>
                </rss>
                """;
    }

    private String img2XmlItem(JSONObject jo, String domain) {
        String _id = jo.getString("_id");
        PageContent pageContent = jo.to(PageContent.class);
        List<String> list = pageContent.getContent();
        String contentHtml = list.stream()
                .map(c -> {
                    if (c.startsWith("http")) {
                        return STR."<img vspace=\"8\" hspace=\"4\" style=\"\" src=\"\{c}\" referrerpolicy=\"no-referrer\">";
                    } else {
                        return STR."<div>\{c}</div>";
                    }
                })
                .collect(Collectors.joining());

        String deleteUrl = STR."<a href=\"http://\{domain}/rss/delete/\{_id}\">删除<a/>";
        contentHtml = STR."\{deleteUrl}<br/>\{contentHtml}<br/>\{deleteUrl}";

        return STR."""
                        <item>
                            <title>
                                <![CDATA[ \{pageContent.getTitle()} ]]>
                            </title>
                            <description>
                                <![CDATA[ \{contentHtml} ]]>
                            </description>
                            <pubDate>\{pageContent.getPubDate()}</pubDate>
                            <link>\{pageContent.getUrl()}</link>
                            <guid>\{_id}</guid>
                            <author>
                                <![CDATA[ 未知 ]]>
                            </author>
                        </item>\n
                """;
    }

    public boolean deleteById(Long id) {
        Criteria criteria = Criteria.where("_id").is(id);
        Update update = Update.update("invalid", 1);
        UpdateResult r = mongoTemplate.updateFirst(Query.query(criteria), update, TABLE_NAME_FEED);
        return r.getModifiedCount() != 0;
    }

    public void pull() throws Exception {
        CommonKeyValue record = new CommonKeyValue();
        record.setBizType(CommonKVBizType.INDEX_SPIDER.getBizType());
        record.setInvalid(0);
        List<CommonKeyValue> commonKeyValues = keyValueMapper.selectList(record);

        for (CommonKeyValue c : commonKeyValues) {
            String key = c.getCommonKey(), paramJson = c.getCommonValue();
            Runnable runnable = () -> {
                log.info("spider拉取:{}", key);
                List<JSONObject> items = MoreFunctions.catching(() ->
                        spiderIndexPage(key, paramJson)
                );
                for (JSONObject item : CollectionUtils.emptyIfNull(items)) {
                    MoreFunctions.runCatching(() -> {
                        mongoTemplate.save(item, TABLE_NAME_FEED);
                    });
                }
                // delete(key, 500);
            };
            Thread.ofVirtual().name(key).start(runnable);
        }
    }

    public void delete(String key, long skip) {
        Criteria criteria = Criteria.where(columnName).is(key);
        Query query = Query.query(criteria).with(Sort.by(Sort.Direction.DESC, "_id")).skip(skip).limit(1);
        JSONObject one = mongoTemplate.findOne(query, JSONObject.class, TABLE_NAME_FEED);
        Long id = MapUtils.getLong(one, "_id", 0L);

        criteria = criteria.and("_id").lte(id);
        mongoTemplate.remove(Query.query(criteria), TABLE_NAME_FEED);
    }

    /**
     * <pre>
     * {
     *   "_id": {
     *     "$numberLong": "4990743676460066"
     *   },
     *   "startUrl": "https://xxx.com/page/2/",
     *   "url": "",
     *   "title": "",
     *   "content": [
     *     ""
     *   ]
     * }
     * </pre>
     */
    private List<JSONObject> spiderIndexPage(String key, String paramJson) throws IOException {
        Criteria criteria = Criteria.where(columnName).is(key)
                .and("content").exists(true).not().size(0);
        Query query = Query.query(criteria).with(Sort.by(Sort.Direction.DESC, "_id")).limit(2000);
        query.fields().include("url", "content");
        List<JSONObject> list = mongoTemplate.find(query, JSONObject.class, TABLE_NAME_FEED);
        Set<String> ignoreIndexItemUrlSet = list.stream()
                .filter(jo -> CollectionUtils.isNotEmpty(jo.getJSONArray("content")))
                .map(jo -> jo.getString("url"))
                .collect(Collectors.toSet());

        List<PageContent> items = SpiderParam.of(paramJson)
                .setIgnoreIndexItemUrlSet(ignoreIndexItemUrlSet)
                .visit();
        return items.stream()
                .map(JSONObject::from)
                .peek(from -> {
                    from.put("_id", from.get("id"));
                    from.put(columnName, key);
                })
                .collect(Collectors.toList());
    }
}
