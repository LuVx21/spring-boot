package org.luvx.boot.tools.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.luvx.boot.tools.BaseAppTests;
import org.luvx.boot.tools.service.oss.OssService;
import org.luvx.boot.tools.service.rss.RssService;

class ServiceTest extends BaseAppTests {
    @Resource
    private OssService ossService;
    @Resource
    private RssService rssService;

    @Test
    void deleteTest() {
    }
}
