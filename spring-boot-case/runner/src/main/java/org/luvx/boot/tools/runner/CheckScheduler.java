package org.luvx.boot.tools.runner;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.service.retrofit.WeiboService;
import org.luvx.boot.tools.service.rss.RssService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Map;

@Slf4j
@Service
public class CheckScheduler {
    @Resource
    protected StringRedisTemplate stringRedisTemplate;

    @Scheduled(cron = "0 1/10 * * * ?")
    public void pullHotBandTask() throws Exception {
        Object o = stringRedisTemplate.opsForValue().get("foo");
        System.out.println("------------");
        System.out.println(o);
        System.out.println("------------");
    }
}
