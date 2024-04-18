package org.luvx.boot.tools.runner;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.service.retrofit.WeiboService;
import org.luvx.boot.tools.service.rss.RssService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Slf4j
@Service
public class RssScheduler {
    @Resource
    private WeiboService weiboService;
    @Resource
    private RssService   rssService;

    // @Scheduled(cron = "0 0/10 9-23 * * ?")
    // public void exec() throws Exception {
    //     weiboService.pullByGroup();
    // }

    // @Scheduled(cron = "0 1/10 * * * ?")
    public void pullHotBandTask() throws Exception {
        weiboService.pullHotBand();
    }

    // @Scheduled(cron = "0 0/60 * * * ?")
    public void delete() throws Exception {
        weiboService.delete(8_000);
    }

    @Scheduled(cron = "0 11 0/2 * * ?")
    public void rss() throws Exception {
        rssService.pull();
    }
}
