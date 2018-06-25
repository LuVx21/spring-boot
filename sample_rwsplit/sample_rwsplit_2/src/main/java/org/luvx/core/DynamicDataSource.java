package org.luvx.core;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 动态数据源实现读写分离
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    //写数据源
    private Object writeDataSource;
    //多个读数据源
    private List<Object> readDataSources;
    //读数据源个数
    private int readDataSourceSize;
    //获取读数据源方式，0：随机，1：轮询
    private int readDataSourcePollPattern = 0;

    private AtomicLong counter = new AtomicLong(0);
    private static final Long MAX_POOL = Long.MAX_VALUE;
    private final Lock lock = new ReentrantLock();

    public void setWriteDataSource(Object writeDataSource) {
        this.writeDataSource = writeDataSource;
    }

    public void setReadDataSources(List<Object> readDataSources) {
        this.readDataSources = readDataSources;
    }

    public void setReadDataSourcePollPattern(int readDataSourcePollPattern) {
        this.readDataSourcePollPattern = readDataSourcePollPattern;
    }

    @Override
    public void afterPropertiesSet() {
        // 写数据源
        if (this.writeDataSource == null) {
            throw new IllegalArgumentException("Property 'writeDataSource' is required");
        }
        setDefaultTargetDataSource(writeDataSource);
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(RorW.WRITE.name(), writeDataSource);

        // 读数据源
        int size = readDataSources.size();
        if (this.readDataSources == null || size == 0) {
            readDataSourceSize = 0;
        } else {
            for (int i = 0; i < size; i++) {
                targetDataSources.put(RorW.READ.name() + i, readDataSources.get(i));
            }
            readDataSourceSize = size;
        }
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        RorW rorW = DynamicDataSourceHolder.getDataSource();

        if (rorW == null
                || rorW == RorW.WRITE
                || readDataSourceSize <= 0) {
            return RorW.WRITE.name();
        }

        int index = 1;

        if (readDataSourcePollPattern == 1) {
            //轮询方式
            long currValue = counter.incrementAndGet();
            if ((currValue + 1) >= MAX_POOL) {
                try {
                    lock.lock();
                    if ((currValue + 1) >= MAX_POOL) {
                        counter.set(0);
                    }
                } finally {
                    lock.unlock();
                }
            }
            index = (int) (currValue % readDataSourceSize);
        } else {
            //随机方式
            index = ThreadLocalRandom.current().nextInt(0, readDataSourceSize);
        }
        return rorW.name() + index;
    }
}