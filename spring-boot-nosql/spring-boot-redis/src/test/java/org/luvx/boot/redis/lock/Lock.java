package org.luvx.boot.redis.lock;

import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.RedisAppTests;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public class Lock extends RedisAppTests {
    @Test
    void m1() {
        RLock lock = redissonClient.getLock("lock-1");
        try {
            boolean tryLock = lock.tryLock(6, 5, TimeUnit.SECONDS);
            if (tryLock) {
                System.out.println("******************** Business ********************");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {   // 获取锁的线程才能解锁
                lock.unlock();
            } else {
                // 没有获得锁的处理
            }
        }
    }
}
