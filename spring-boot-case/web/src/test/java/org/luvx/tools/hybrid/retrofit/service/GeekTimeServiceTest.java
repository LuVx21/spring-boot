package org.luvx.tools.hybrid.retrofit.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.luvx.tools.BaseAppTests;

import io.vavr.Tuple2;

class GeekTimeServiceTest extends BaseAppTests {
    @Resource
    private GeekTimeService geekTimeService;

    @Test
    void main() throws Exception {
        geekTimeService.allCourse();
        List<Long> set = Stream.of(
                )
                .distinct()
                .sorted()
                .map(Object::toString)
                .map(Long::valueOf)
                .collect(Collectors.toList());
        int type = 1;
        if (type == 0) {
            // 所有文章
            geekTimeService.downloadCourse(set);
        } else if (type == 1) {
            // 指定文章
            for (Long courseId : set) {
                List<Long> ids = geekTimeService.getUpdateArticles(courseId);
                for (Long articleId : ids) {
                    geekTimeService.downloadArticle(courseId, articleId);
                }
            }
        }
    }
}