package org.luvx.boot.mars.service.retrofit;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.luvx.boot.mars.service.retrofit.WeiboService;
import org.luvx.boot.mars.BaseAppTests;

public class WeiboServiceTest extends BaseAppTests {
    @Resource
    private WeiboService service;

    @Test
    void rssTest() {
        String rss = service.rss();
        System.out.println(rss);
    }

    @Test
    void m1() {
        service.pullByGroup();
        service.delete(2_000);
    }
}
