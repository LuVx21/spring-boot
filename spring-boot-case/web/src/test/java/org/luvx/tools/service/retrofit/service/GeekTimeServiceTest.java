package org.luvx.tools.service.retrofit.service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.luvx.tools.BaseAppTests;

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
        geekTimeService.getCommentOfArticle("69636");
    }
}