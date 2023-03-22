package org.luvx.boot.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author Ren, Xie
 */
public class ZookeeperCuratorClient implements InitializingBean {
    private CuratorFramework curatorClient;
    @Value("${zookeeper.address:localhost:2181}")
    private String connectString;
    @Value("${zookeeper.baseSleepTimeMs:1000}")
    private int baseSleepTimeMs;
    @Value("${zookeeper.maxRetries:3}")
    private int maxRetries;
    @Value("${zookeeper.sessionTimeoutMs:6000}")
    private int sessionTimeoutMs;
    @Value("${zookeeper.connectionTimeoutMs:6000}")
    private int connectionTimeoutMs;

    @Override
    public void afterPropertiesSet() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        curatorClient = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(connectionTimeoutMs)
                .retryPolicy(retryPolicy)
                .build();
        curatorClient.start();
    }

    @Bean
    public CuratorFramework getCuratorClient() {
        return curatorClient;
    }
}