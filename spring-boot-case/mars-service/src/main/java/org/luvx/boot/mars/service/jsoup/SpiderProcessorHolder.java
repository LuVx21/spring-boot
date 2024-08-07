package org.luvx.boot.mars.service.jsoup;

import org.jsoup.nodes.Element;
import org.luvx.boot.mars.dao.mongo.rss.PageContent;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SpiderProcessorHolder {
    public static final Map<String, Function<List<Element>, List<Element>>> indexItemListPostProcessorMap = Map.of(
            "reversed", List::reversed
    );

    public static final Map<String, Function<String, String>> indexNextPageUrlPostProcessorMap = Map.of(
    );

    public static final Map<String, Function<PageContent, PageContent>> contentPostProcessorMap = Map.of(
    );

    public static final Map<String, Function<String, String>> contentNextPageUrlPostProcessorMap = Map.of(
    );
}
