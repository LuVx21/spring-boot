package org.luvx.boot.mars.service.jsoup;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.RateLimiter;
import jakarta.annotation.Nonnull;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.luvx.boot.mars.common.CCC;
import org.luvx.boot.mars.dao.mongo.rss.PageContent;
import org.luvx.boot.mars.service.jsoup.SpiderParam.QueryRule;
import org.luvx.coding.common.net.UrlUtils;
import org.luvx.coding.common.util.Asserts;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.luvx.coding.common.retryer.RetryUtils.supplyWithRetry;
import static org.luvx.coding.common.util.Predicates.alwaysFalse;

@Slf4j
@ToString
@Accessors(fluent = true)
public class PageContentSpider {
    private final RateLimiter limiter = RateLimiter.create(1);

    @Nonnull
    private final SpiderParam param;

    private PageContentSpider() {
        throw new UnsupportedOperationException("");
    }

    public PageContentSpider(@Nonnull SpiderParam param) {
        this.param = param;
    }

    public PageContent content(String title, String url) throws IOException {
        List<String> contentList = Lists.newArrayList();
        String pubDate = "";
        Set<String> categorySet = Sets.newHashSet();
        String pageUrl = url;
        for (int i = 0; isNotBlank(pageUrl); i++) {
            log.info("解析内容页: No.{} {}-{}", i + 1, title, URLDecoder.decode(pageUrl, StandardCharsets.UTF_8));
            limiter.acquire();
            Connection connect = Jsoup.connect(pageUrl).timeout(60_000);
            Document doc = supplyWithRetry("解析内容页重试", connect::get, null, 5, Duration.ofSeconds(5), alwaysFalse());
            if (doc == null) {
                break;
            }
            if (i == 0) {
                QueryRule contentTitleRule = param.getContentTitleRule();
                if (contentTitleRule != null) {
                    title = getValue(doc, contentTitleRule);
                }
                QueryRule rule = param.getContentPubDateRule();
                if (rule != null) {
                    pubDate = getValue(doc, rule);
                }
                List<QueryRule> contentCategoryRuleList = param.getContentCategoryRuleList();
                if (isNotEmpty(contentCategoryRuleList)) {
                    contentCategoryRuleList.stream()
                            .map(_rule -> getValueListAfterSelect(doc, _rule))
                            .flatMap(List::stream)
                            .forEachOrdered(categorySet::add);
                }
            }
            QueryRule rule = param.getContentRule();
            Elements a = doc.select(rule.getElementQuery());
            for (Element element : a) {
                contentList.add(getValue(element, rule.getValueQuery()));
            }

            String finalPageUrl = pageUrl;
            pageUrl = Optional.ofNullable(param.getContentNextPageUrlRule())
                    .map(r -> getValue(doc, r))
                    .filter(StringUtils::isNotBlank)
                    .map(aa -> UrlUtils.urlAddDomain(url, aa))
                    .map(param.getContentNextPageUrlPostProcessor())
                    .filter(u -> !finalPageUrl.equals(u))
                    .orElse(null);
        }
        PageContent page = new PageContent(
                CCC.defaultIdWorker.nextId(), param.getSpiderKey(),
                url, title, pubDate, categorySet, contentList, 0, LocalDateTime.now()
        );
        return param.getContentPostProcessor().apply(page);
    }

    public List<PageContent> visit(String url) throws IOException {
        checkNotNull(url, "空:起始链接");
        checkNotNull(param, "空:规则参数");
        checkNotNull(param.getIndexItemListRule(), "空:章节list规则");
        checkNotNull(param.getIndexItemTitleRule(), "空:章节title规则");
        checkNotNull(param.getIndexItemUrlRule(), "空:章节url规则");
        checkNotNull(param.getContentRule(), "空:文章规则");

        List<PageContent> result = Lists.newArrayList();
        String pageUrl = url;
        for (int i = 0; i < param.getPageCount() && isNotBlank(pageUrl); i++) {
            log.info("解析目录页: {}", pageUrl);
            limiter.acquire();
            Connection connect = Jsoup.connect(pageUrl).timeout(60_000);
            Document doc = supplyWithRetry("解析目录页重试", connect::get, null, 5, Duration.ofSeconds(5), alwaysFalse());
            if (doc == null) {
                return result;
            }
            List<Element> indexList = param.getIndexItemListPostProcessor().apply(doc.select(param.getIndexItemListRule()));
            if (CollectionUtils.isEmpty(indexList)) {
                return result;
            }
            int max = Math.min(indexList.size(), param.getCountInPage());
            for (int k = 0; k < max; k++) {
                Element element = indexList.get(k);
                String title = getValue(element, param.getIndexItemTitleRule());

                String href = getValue(element, param.getIndexItemUrlRule());
                log.debug("目录页内容: {} {} {}", k, title, href);
                if (isBlank(href) || param.getIgnoreIndexItemUrlSet().contains(href)) {
                    continue;
                }
                PageContent content = content(title, href);
                log.debug("内容页内容: \n{}", content.getContent());
                if (CollectionUtils.isEmpty(content.getContent())) {
                    continue;
                }
                param.getIgnoreIndexItemUrlSet().add(content.getUrl());
                result.add(content);
            }

            String finalPageUrl = pageUrl;
            pageUrl = Optional.ofNullable(param.getIndexNextPageUrlRule())
                    .map(r -> getValue(doc, r))
                    .filter(StringUtils::isNotBlank)
                    .map(aa -> UrlUtils.urlAddDomain(url, aa))
                    .map(param.getIndexNextPageUrlPostProcessor())
                    .filter(u -> !finalPageUrl.equals(u))
                    .orElse(null);
        }
        return result;
    }

    private List<String> getValueListAfterSelect(Element element, QueryRule rule) {
        Preconditions.checkNotNull(rule, "空指针");
        String elementQuery = rule.getElementQuery();
        Asserts.checkNotBlank(elementQuery, "元素查找规则为空");

        String[] split = elementQuery.split("\\|");
        for (String eq : split) {
            Elements es = element.select(eq);
            List<String> list = es.stream()
                    .map(e -> getValue(e, rule.getValueQuery()))
                    .collect(Collectors.toList());
            if (isNotEmpty(list)) {
                return list;
            }
        }
        return Lists.newArrayList();
    }

    /**
     * 对element查找后再获取属性
     */
    private String getValue(Element element, QueryRule rule) {
        if (rule == null) {
            return "";
        }
        String elementQuery = rule.getElementQuery();
        if (isBlank(elementQuery)) {
            return getValue(element, rule.getValueQuery());
        }
        String[] split = elementQuery.split("\\|");
        for (String eq : split) {
            Element _e = element.selectFirst(eq);
            if (_e == null) {
                continue;
            }
            String value = getValue(_e, rule.getValueQuery());
            if (isNotEmpty(value)) {
                return value;
            }
        }
        return "";
    }

    /**
     * 不再对element进行查找直接获取属性
     */
    private String getValue(Element element, String elementQuery) {
        if (element == null || isBlank(elementQuery)) {
            return "";
        }

        String[] split = elementQuery.split("\\|");
        String result = "";
        for (String q : split) {
            result = switch (q) {
                case "text" -> element.text();
                case "data" -> element.data();
                case "href" -> element.absUrl(q);
                default -> element.attr(q);
            };
            if (isNotEmpty(result)) {
                break;
            }
        }
        return result;
    }
}
