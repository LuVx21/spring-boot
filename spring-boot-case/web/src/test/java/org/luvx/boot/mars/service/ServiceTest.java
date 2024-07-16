package org.luvx.boot.mars.service;

import org.junit.jupiter.api.Test;
import org.luvx.boot.mars.BaseAppTests;
import org.luvx.boot.mars.service.oss.OssService;
import org.luvx.boot.mars.service.retrofit.BilibiliService;
import org.luvx.boot.mars.service.rss.RssService;

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
