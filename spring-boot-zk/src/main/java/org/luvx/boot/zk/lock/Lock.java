package org.luvx.boot.zk.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.Locker;
import org.apache.curator.retry.ExponentialBackoffRetry;

import lombok.extern.slf4j.Slf4j;

/**
 * 分布式锁
 *
 * @author Ren, Xie
 */
@Slf4j
public class Lock {
    private final String url = "127.0.0.1:2181";

    /**
     * 自定义实现-非公平
     */
    private void unFair() {
    }

    /**
     * 自定义实现-公平
     */
    private void fair() {
    }

    /**
     * curator 提供的实现:
     * InterProcessMultiLock
     * InterProcessMutex
     * InterProcessSemaphoreMutex
     * <p>
     * InterProcessReadWriteLock
     */
    private void a() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        try (CuratorFramework client = CuratorFrameworkFactory.newClient(url, retryPolicy)) {
            client.start();
            InterProcessMutex lock = new InterProcessMutex(client, "/lock");
            try (Locker locker = new Locker(lock)) {
                log.info("获取到锁后的业务逻辑...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
    }
}
