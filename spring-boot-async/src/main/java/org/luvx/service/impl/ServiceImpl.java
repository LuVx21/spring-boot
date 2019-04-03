package org.luvx.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.luvx.service.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

/**
 * @ClassName: org.luvx.service.impl
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/2 16:11
 */
@Slf4j
@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    @Override
    public String method0() {
        log.info("service 同步方法 start 当前线程:{}", Thread.currentThread().getName());
        some();
        log.info("service 同步方法 end 当前线程:{}", Thread.currentThread().getName());
        return "耗时操作";
    }

    @Async
    @Override
    public String method1() {
        log.info("service 异步方法 start 当前线程:{}", Thread.currentThread().getName());
        some();
        log.info("service 异步方法 end 当前线程:{}", Thread.currentThread().getName());
        return "耗时操作";
    }

    @Async
    @Override
    public Future<String> method2() {
        log.info("service 异步方法 start 当前线程:{}", Thread.currentThread().getName());
        some();
        Future<String> future = new AsyncResult<>("异步方法返回值");
        log.info("service 异步方法 end 当前线程:{}", Thread.currentThread().getName());
        return future;
    }

    private void some() {
        try {
            Thread.sleep(15 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
