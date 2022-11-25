package org.luvx.tools.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.MapUtils;
import org.luvx.boot.web.response.R;
import org.luvx.tools.service.retrofit.interceptor.GeekTimeInterceptor;
import org.luvx.tools.service.retrofit.service.GeekTimeService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ToolsController {
    @Resource
    private GeekTimeService geekTimeService;

    @RequestMapping(value = {"tool/in"}, method = {RequestMethod.POST})
    public R<Object> index(@RequestBody Map<String, List<String>> param) {
        Set<String> left = Sets.newHashSet(param.getOrDefault("left", Collections.emptyList()));
        Set<String> right = Sets.newHashSet(param.getOrDefault("right", Collections.emptyList()));
        return R.success(Map.of(
                "left-right", Sets.difference(left, right),
                "right-left", Sets.difference(right, left),
                "left∩right", Sets.intersection(left, right)
        ));
    }

    @RequestMapping(value = {"tool/geektime"}, method = {RequestMethod.POST})
    public R<Object> geektime(@RequestBody Map<String, Object> param) throws Exception {
        String cookie = MapUtils.getString(param, "cookie");
        GeekTimeInterceptor.setCookie(cookie);
        List<String> idList = (List<String>) MapUtils.getObject(param, "ids", Collections.emptyList());

        geekTimeService.allCourse();
        List<Long> set = idList.stream()
                .distinct()
                .sorted()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        int type = 1;
        if (type == 0) {
            // 所有文章
            geekTimeService.downloadCourse(set);
        } else if (type == 1) {
            // 指定文章
            for (Long courseId : set) {
                List<Long> ids = geekTimeService.getUpdateArticleIds(courseId);
                for (Long articleId : ids) {
                    geekTimeService.downloadArticle(courseId, articleId);
                }
            }
        }
        return R.success("ok");
    }
}
