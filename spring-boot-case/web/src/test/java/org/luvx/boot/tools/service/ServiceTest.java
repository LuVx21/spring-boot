package org.luvx.boot.tools.service;

import org.junit.jupiter.api.Test;
import org.luvx.boot.tools.BaseAppTests;
import org.luvx.boot.tools.service.oss.OssService;
import org.luvx.boot.tools.service.retrofit.BilibiliService;
import org.luvx.boot.tools.service.rss.RssService;

import jakarta.annotation.Resource;

class ServiceTest extends BaseAppTests {
    @Resource
    private OssService      ossService;
    @Resource
    private RssService      rssService;
    @Resource
    private BilibiliService bilibiliService;

    @Test
    void deleteTest() {
    }
}
