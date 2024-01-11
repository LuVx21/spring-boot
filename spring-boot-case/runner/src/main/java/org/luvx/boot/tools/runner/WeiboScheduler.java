package org.luvx.boot.tools.runner;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.service.retrofit.WeiboService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeiboScheduler {
    @Resource
    private WeiboService service;

    @Scheduled(cron = "0 0/10 9-23 * * ?")
    public void exec() throws Exception {
        service.pullByGroup();
    }

    @Scheduled(cron = "0 0/60 * * * ?")
    public void delete() throws Exception {
        service.delete(2_000);
    }
}