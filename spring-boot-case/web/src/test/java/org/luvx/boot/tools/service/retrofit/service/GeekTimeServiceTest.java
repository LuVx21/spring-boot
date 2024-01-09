package org.luvx.boot.tools.service.retrofit.service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.luvx.boot.tools.service.retrofit.GeekTimeService;
import org.luvx.boot.tools.BaseAppTests;

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
                List<Long> ids = geekTimeService.getUpdateArticleIds(courseId);
                for (Long articleId : ids) {
                    geekTimeService.downloadArticle(courseId, articleId);
                }
            }
        }
    }

    @Test
    void comments() {
        Stream.of("")
                .forEachOrdered(geekTimeService::getCommentOfArticle);
    }
}