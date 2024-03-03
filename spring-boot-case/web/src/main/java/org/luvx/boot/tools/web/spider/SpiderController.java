package org.luvx.boot.tools.web.spider;

import jakarta.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.luvx.boot.tools.service.api.interceptor.GeekTimeInterceptor;
import org.luvx.boot.tools.service.retrofit.GeekTimeService;
import org.luvx.boot.web.response.R;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/spider")
@RestController
public class SpiderController {
    @Resource
    private GeekTimeService geekTimeService;

    @RequestMapping(value = {"/geektime"}, method = {RequestMethod.POST})
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
                try {
                    List<Long> ids = geekTimeService.getUpdateArticleIds(courseId);
                    for (Long articleId : ids) {
                        geekTimeService.downloadArticle(courseId, articleId);
                    }
                } catch (Exception ignore) {
                    log.warn("当前课程异常,忽略后继续下一课程", ignore);
                }
            }
        }
        return R.success("ok");
    }
}
