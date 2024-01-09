package org.luvx.controller;

import lombok.extern.slf4j.Slf4j;
import org.luvx.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: org.luvx.controller
 * @Description: 3种方式实现异步请求
 * @Author: Ren, Xie
 * @Date: 2019/4/2 16:08
 */
@Slf4j
@RestController
public class ControllerCase {

    @Autowired
    Service service;

    /**
     * 方式1: 使用Callable
     * 默认情况下会一直创建新线程处理异步请求
     * 因此需要配置线程池和超时时间才可以
     *
     * @return
     */
    @RequestMapping(value = {"/callable"}, method = RequestMethod.GET)
    public Callable<String> callable() {
        log.info("controller start 当前线程：{}", Thread.currentThread().getName());
        Callable<String> callable = () -> {
            log.info("新线程:{}", Thread.currentThread().getName());
            String str = service.method0();
            return str;
        };
        log.info("controller end 当前线程：{}", Thread.currentThread().getName());
        return callable;
    }

    /**
     * 可以在新线程和当前线程间通信
     *
     * @return
     */
    @RequestMapping(value = {"/deferred"}, method = RequestMethod.GET)
    public DeferredResult<String> deferredResult() {
        log.info("controller start 当前线程：{}", Thread.currentThread().getName());
        DeferredResult<String> result = new DeferredResult<>(60 * 1000L);

        result.onTimeout(() ->
                log.error("任务超时:{}", Thread.currentThread().getName())
        );
        result.onCompletion(() ->
                log.error("任务正常结束:{}", Thread.currentThread().getName())
        );

        Executors.newFixedThreadPool(30).execute(() -> {
            log.info("新线程：" + Thread.currentThread().getName());
            String str = service.method0();
            result.setResult(str);
        });

        log.info("controller end 当前线程：{}", Thread.currentThread().getName());
        return result;
    }

    /**
     * WebAsyncTask方式
     *
     * @return
     */
    @RequestMapping(value = {"/webasync"}, method = RequestMethod.GET)
    public WebAsyncTask<String> webAsyncTask() {
        log.info("controller start 当前线程：{}", Thread.currentThread().getName());
        WebAsyncTask<String> result = new WebAsyncTask<>(60 * 1000L, () -> {
            log.info("新线程:{}", Thread.currentThread().getName());
            String str = service.method0();
            return str;
        });

        result.onTimeout(() -> {
            log.error("任务超时:{}", Thread.currentThread().getName());
            return "任务超时";
        });
        result.onCompletion(() ->
                log.error("任务正常结束:{}", Thread.currentThread().getName())
        );

        log.info("controller end 当前线程：{}", Thread.currentThread().getName());
        return result;
    }

    /**
     * 异步调用
     *
     * @return
     */
    @RequestMapping(value = {"/async"}, method = RequestMethod.GET)
    public String async() {
        log.info("controller start 当前线程：{}", Thread.currentThread().getName());

        Instant start = Instant.now();
        log.info("同步方法执行开始");
        service.method0();
        Instant end = Instant.now();
        log.info("同步方法执行结束:{}", Duration.between(start, end).getSeconds());
        service.method1();
        Instant endend = Instant.now();
        log.info("异步方法执行结束:{}", Duration.between(end, endend).getSeconds());

        log.info("controller end 当前线程：{}", Thread.currentThread().getName());
        return "";
    }

    /**
     * 异步调用/回调
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/async1"}, method = RequestMethod.GET)
    public String async1() throws Exception {
        log.info("controller start 当前线程：{}", Thread.currentThread().getName());

        Instant start = Instant.now();
        Future<String> future = service.method2();
        Instant end = Instant.now();
        log.info("同步方法执行结束:{}", Duration.between(start, end).getSeconds());

        String str = future.get(60, TimeUnit.SECONDS);
        log.info("controller end 当前线程：{}", Thread.currentThread().getName());
        return str;
    }
}
