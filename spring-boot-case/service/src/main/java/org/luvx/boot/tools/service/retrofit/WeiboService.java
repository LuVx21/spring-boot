package org.luvx.boot.tools.service.retrofit;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.github.phantomthief.util.MoreFunctions;
import com.google.common.collect.Iterators;
import com.google.common.util.concurrent.RateLimiter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.luvx.boot.common.app.AppInfo;
import org.luvx.boot.tools.service.api.WeiboApi;
import org.luvx.boot.tools.service.commonkv.CommonKeyValueService;
import org.luvx.boot.tools.service.commonkv.constant.CommonKVBizType;
import org.luvx.boot.tools.service.oss.OssScopeEnum;
import org.luvx.boot.tools.service.oss.OssService;
import org.luvx.coding.common.consts.Common;
import org.luvx.coding.common.cursor.CursorIteratorEx;
import org.luvx.coding.common.net.HttpUtils;
import org.luvx.coding.common.net.UrlUtils;
import org.luvx.coding.common.util.JSONPathUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.luvx.boot.tools.service.oss.OssService.IMG_HOME;

@Slf4j
@Service
public class WeiboService {
    private static final String TABLE_NAME_FEED = "weibo_feed";

    @Resource
    private WeiboApi              weiboApi;
    @Resource
    private CommonKeyValueService keyValueService;
    @Resource
    private OssService            ossService;
    @Resource
    private MongoTemplate         mongoTemplate;

    public void hotBand() {
        String s = weiboApi.hotBand();
    }

    public String rss() {
        int bound = 4;
        int i = ThreadLocalRandom.current().nextInt(0, bound);
        if (i % bound == 0) {
            pullByGroup();
        }

        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "_id")).limit(200);
        List<JSONObject> feedList = mongoTemplate.find(query, JSONObject.class, TABLE_NAME_FEED);
        String s = feedList.stream()
                .map(this::a)
                .collect(Collectors.joining());

        return STR."""
                <?xml version="1.0" encoding="UTF-8"?>
                <rss xmlns:atom="http://www.w3.org/2005/Atom" version="2.0">
                    <channel>
                        <title><![CDATA[网络傻事]]></title>

                \{s}
                    </channel>
                </rss>
                """;
    }

    private String a(JSONObject jo) {
        String title = jo.getString("text_raw");

        String contentHtml = contentHtml(jo);
        JSONObject retweet = jo.getJSONObject("retweeted_status");
        if (retweet != null) {
            contentHtml = STR."\{contentHtml}<hr/>转发自<br/><br/><br/>\{contentHtml(retweet)}";
        }

        String createdAt = jo.getString("created_at");

        JSONObject user = jo.getJSONObject("user");
        String screenName = user.getString("screen_name");
        String userId = user.getString("idstr");
        String url = STR."https://weibo.com/\{userId}/\{jo.getString("mblogid")}";

        return STR."""
                        <item>
                            <title>
                                <![CDATA[ \{title} ]]>
                            </title>
                            <description>
                                <![CDATA[ \{contentHtml} ]]>
                            </description>
                            <pubDate>\{createdAt}</pubDate>
                            <link>\{url}</link>
                            <author>
                                <![CDATA[ \{screenName} ]]>
                            </author>
                        </item>\n
                """;
    }

    private String contentHtml(JSONObject jo) {
        String text = jo.getString("text");
        text = text.replaceAll("//<a ", "<br/><br/>//<a ");
        text = text.replaceAll("\n", "<br/>");
        text = text.replaceAll("。", "。<br/>");

        JSONArray picIds = jo.getJSONArray("pic_ids");
        JSONObject picInfos = jo.getJSONObject("pic_infos");

        String picList = "";
        if (CollectionUtils.isNotEmpty(picIds) && picInfos != null) {
            picList = picIds.stream()
                    .map(pid -> {
                        JSONObject jo1 = picInfos.getJSONObject(pid.toString());
                        JSONObject img = ObjectUtils.getIfNull(jo1.getJSONObject("largest"), () -> jo1.getJSONObject("original"));
                        String url = img.getString("url");
                        var fileName = UrlUtils.urlFileName(url);
                        asyncDownload(url, fileName);
                        url = newImgUrl(fileName);
                        return STR."<img vspace=\"8\" hspace=\"4\" style=\"\" src=\"\{url}\" referrerpolicy=\"no-referrer\">";
                    })
                    .collect(Collectors.joining("<br/>"));
        }
        return text + picList;
    }

    private String newImgUrl(String fileName) {
        String ip = NetUtils.getHostInfo().get("ip");
        Integer port = AppInfo.instance().map(AppInfo::getPort).orElse(8080);
        return STR."http://\{ip}:\{port}/oss/\{OssScopeEnum.WEIBO.getCode()}/\{fileName}";
    }

    public void asyncDownload(String url, String fileName) {
        String parent = STR."\{IMG_HOME}/\{OssScopeEnum.WEIBO.getCode()}/";
        String s = STR."\{parent}\{fileName}";
        File file = new File(s);
        if (file.exists()) {
            return;
        }
        CompletableFuture.runAsync(() -> {
            MoreFunctions.runCatching(() -> {
                HttpUtils.download(url, parent, fileName, 0);
                ossService.add(OssScopeEnum.WEIBO, fileName);
            });
        }, Common.THREAD_POOL_EXECUTOR_SUPPLIER.get());
    }

    public void pullByGroup() {
        log.info("拉取微博feed流");
        String weiboCookie = keyValueService.<String>getData(CommonKVBizType.STRING, "weibo_cookie")
                .orElseThrow(() -> new RuntimeException("微博cookie过期,使用key: weibo_cookie更新cookie"));

        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "_id")).skip(0).limit(1);
        Map<String, Object> map = (Map<String, Object>) mongoTemplate.findOne(query, Object.class, TABLE_NAME_FEED);
        Long currentMaxId = MapUtils.getLong(map, "_id");

        final int limit = 25;
        CursorIteratorEx<JSONObject, Long, JSONObject> cursorIterator = CursorIteratorEx.<JSONObject, Long, JSONObject>builder()
                .withInitCursor(null)
                .withEndChecker(Objects::isNull)
                .withRateLimiter(RateLimiter.create(0.16))
                .withDataAccessor((Long _cursor) -> {
                    Map<String, Object> queryMap = _cursor != null ? Map.of("max_id", _cursor) : Collections.emptyMap();
                    String json = weiboApi.byGroup(weiboCookie, 4670120389774996L, 4, 1, limit, queryMap);
                    return JSON.parseObject(json);
                })
                .withDataExtractor(root -> {
                    JSONArray feedList = root.getJSONArray("statuses");
                    return Iterators.transform(feedList.iterator(), feed -> (JSONObject) feed);
                })
                .withCursorExtractor((JSONObject root) -> {
                    // 库中没有数据
                    JSONArray feedList;
                    if (currentMaxId == null || CollectionUtils.size(feedList = root.getJSONArray("statuses")) < limit) {
                        return null;
                    }
                    Long last = ((JSONObject) feedList.getLast()).getLong("id");
                    if (currentMaxId >= last) {
                        return null;
                    }
                    return root.getLong("max_id");
                }).build();

        cursorIterator.forEach(feed -> {
            Long id = feed.getLong("id");
            if (currentMaxId != null && id <= currentMaxId) {
                return;
            }
            feed.put("_id", id);
            aa(feed, weiboCookie);
            aa(feed.getJSONObject("retweeted_status"), weiboCookie);

            mongoTemplate.save(feed.toJSONString(), TABLE_NAME_FEED);
        });
    }

    private void aa(JSONObject feed, String weiboCookie) {
        if (feed == null) {
            return;
        }
        String text = feed.getString("text");
        if (text.contains(">展开<")) {
            String mblogid = feed.getString("mblogid");
            Common.RATE_LIMITER_SUPPLIER.get().acquire();
            String json = MoreFunctions.catching(() -> weiboApi.longText(weiboCookie, mblogid));
            text = Optional.ofNullable(json)
                    .map(j -> JSONPathUtils.get(j, "$.data.longTextContent"))
                    .map(Object::toString)
                    .orElse(text);
            feed.put("text", text);
        }
    }

    public void delete(long skip) {
        log.info("删除多余微博feed");
        long count = mongoTemplate.count(new Query(), TABLE_NAME_FEED);
        if (count <= skip) {
            return;
        }

        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "_id")).skip(skip).limit(1);
        Map<String, Object> map = (Map<String, Object>) mongoTemplate.findOne(query, Object.class, TABLE_NAME_FEED);
        Long id = MapUtils.getLong(map, "_id");

        Criteria criteria = Criteria.where("_id").lt(id);
        mongoTemplate.remove(Query.query(criteria), TABLE_NAME_FEED);
    }
}
