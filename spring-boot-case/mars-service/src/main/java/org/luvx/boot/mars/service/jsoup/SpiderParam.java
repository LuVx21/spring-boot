package org.luvx.boot.mars.service.jsoup;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.luvx.boot.mars.dao.mongo.rss.PageContent;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.function.Function.identity;
import static org.luvx.boot.mars.service.jsoup.SpiderProcessorHolder.*;

/**
 * <pre>
 * {
 *     "startUrl": "https://xxx.com/page/5/",
 *     "pageCount": 5,
 *     "countInPage": 999,
 *     "indexItemListRule": "div.elementor-posts-container article h3.elementor-post__title a",
 *     "indexItemTitleRule": {
 *         "valueQuery": "text",
 *         "elementQuery": ""
 *     },
 *     "indexItemUrlRule": {
 *         "valueQuery": "href",
 *         "elementQuery": ""
 *     },
 *     "indexNextPageUrlRule": {
 *         "valueQuery": "href",
 *         "elementQuery": "nav.elementor-pagination a.prev"
 *     },
 *     "contentRule": {
 *         "valueQuery": "data-src",
 *         "elementQuery": "div.elementor-element-cad010d div.elementor-widget-container > p > img.aligncenter"
 *     },
 *     "indexItemListPostProcessor": "reversed"
 * }
 * </pre>
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class SpiderParam {
    private String spiderKey;
    @Nullable
    private String startUrl;
    /**
     * 翻几页
     */
    private int    pageCount   = 2;
    /**
     * 当页的前几个
     */
    private int    countInPage = 2;

    /**
     * 查找目录页中item规则,通常是找到一组Element
     */
    private String                                 indexItemListRule;
    /**
     * 目录页item处理器,如逆序
     */
    private Function<List<Element>, List<Element>> indexItemListPostProcessor = identity();
    /**
     * 目录页item的title
     */
    private QueryRule                              indexItemTitleRule;
    /**
     * 目录页item的url
     */
    private QueryRule                              indexItemUrlRule;
    /**
     * 通常不是配置,而是代码获取上次的结果
     */
    private Set<String>                            ignoreIndexItemUrlSet      = Sets.newHashSet();

    /**
     * 目录页翻页时下一页url
     */
    @Nullable
    private QueryRule                indexNextPageUrlRule;
    /**
     * 目录页翻页时下一页url的处理器
     */
    private Function<String, String> indexNextPageUrlPostProcessor = identity();

    // ----------------------------------------------------------

    /**
     * 用于覆盖indexItemTitleRule的结果
     */
    @Nullable
    private QueryRule                          contentTitleRule;
    /**
     * 内容发布日期
     */
    @Nullable
    private QueryRule                          contentPubDateRule;
    @Nullable
    private List<QueryRule>                    contentCategoryRuleList;
    /**
     * 内容页查找内容
     */
    private QueryRule                          contentRule;
    /**
     * 内容页查找内容后的处理
     */
    private Function<PageContent, PageContent> contentPostProcessor = identity();

    /**
     * 单一内容页可能翻页
     */
    @Nullable
    private QueryRule                contentNextPageUrlRule;
    /**
     * 内容页翻页url处理器
     */
    private Function<String, String> contentNextPageUrlPostProcessor = identity();

    public PageContentSpider newSpider() {
        return new PageContentSpider(this);
    }

    public List<PageContent> visit() throws IOException {
        Preconditions.checkNotNull(startUrl, "开始连接不可为空");
        return newSpider().visit(startUrl);
    }

    public static SpiderParam of(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        SpiderParam param = jsonObject.to(SpiderParam.class);

        String indexItemListPostProcessor = jsonObject.getString("indexItemListPostProcessor");
        String indexNextPageUrlPostProcessor = jsonObject.getString("indexNextPageUrlPostProcessor");
        String contentPostProcessor = jsonObject.getString("contentPostProcessor");
        String contentNextPageUrlPostProcessor = jsonObject.getString("contentNextPageUrlPostProcessor");

        if (StringUtils.isNotEmpty(indexItemListPostProcessor)) {
            Function<List<Element>, List<Element>> func = indexItemListPostProcessorMap.get(indexItemListPostProcessor);
            Preconditions.checkNotNull(func, "参数配置错误:indexItemListPostProcessor");
            param.setIndexItemListPostProcessor(func);
        }
        if (StringUtils.isNotEmpty(indexNextPageUrlPostProcessor)) {
            Function<String, String> func = indexNextPageUrlPostProcessorMap.get(indexNextPageUrlPostProcessor);
            Preconditions.checkNotNull(func, "参数配置错误:indexNextPageUrlPostProcessor");
            param.setIndexNextPageUrlPostProcessor(func);
        }
        if (StringUtils.isNotEmpty(contentPostProcessor)) {
            Function<PageContent, PageContent> func = contentPostProcessorMap.get(contentPostProcessor);
            Preconditions.checkNotNull(func, "参数配置错误:contentPostProcessor");
            param.setContentPostProcessor(func);
        }
        if (StringUtils.isNotEmpty(contentNextPageUrlPostProcessor)) {
            Function<String, String> func = contentNextPageUrlPostProcessorMap.get(contentNextPageUrlPostProcessor);
            Preconditions.checkNotNull(func, "参数配置错误:contentNextPageUrlPostProcessor");
            param.setContentNextPageUrlPostProcessor(func);
        }
        return param;
    }

    @Getter
    @Setter
    @ToString
    public static class QueryRule {
        /**
         * 找到具体的标签,多个使用|分割
         */
        private String elementQuery;
        /**
         * 获取标签的指定值,多个使用|分割
         */
        private String valueQuery;

        public static QueryRule of(String elementQuery, String valueQuery) {
            QueryRule queryRule = new QueryRule();
            queryRule.elementQuery = elementQuery;
            queryRule.valueQuery = valueQuery;
            return queryRule;
        }
    }
}