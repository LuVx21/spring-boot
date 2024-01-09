package org.luvx.boot.zk.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;

import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {
    @Resource
    private ZookeeperLockRegistry lockRegistry;

    @SneakyThrows
    @GetMapping("/lock")
    public String lock() {
        log.info("获取锁...");
        final Lock lock = lockRegistry.obtain("lock");
        lock.lock();
        try {
            log.info("获取锁成功...");
            TimeUnit.SECONDS.sleep(5);
        } finally {
            lock.unlock();
            log.info("释放锁...");
        }
        return "OK";
    }
}