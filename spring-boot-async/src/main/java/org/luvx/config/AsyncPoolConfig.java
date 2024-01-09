package org.luvx.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: org.luvx.config
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/2 16:24
 */
@Slf4j
@Configuration
public class AsyncPoolConfig extends WebMvcConfigurerAdapter {

    @Bean(name = "asyncPoolTaskExecutor")
    public ThreadPoolTaskExecutor getAsyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(20);
        taskExecutor.setMaxPoolSize(200);
        taskExecutor.setQueueCapacity(25);
        taskExecutor.setKeepAliveSeconds(200);
        taskExecutor.setThreadNamePrefix("MvcAsync-callable-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(60 * 1000);
        configurer.registerCallableInterceptors(timeoutInterceptor());
        configurer.setTaskExecutor(getAsyncThreadPoolTaskExecutor());
    }

    @Bean
    public TimeoutCallableInterceptor timeoutInterceptor() {
        return new TimeoutCallableInterceptor();
    }

    static class TimeoutCallableInterceptor implements CallableProcessingInterceptor {
        @Override
        public <T> void beforeConcurrentHandling(NativeWebRequest request, Callable<T> task) throws Exception {
        }

        @Override
        public <T> void preProcess(NativeWebRequest request, Callable<T> task) throws Exception {
        }

        @Override
        public <T> void postProcess(NativeWebRequest request, Callable<T> task, Object concurrentResult) throws Exception {
        }

        @Override
        public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task) throws Exception {
            log.error("任务超时:{}", task.getClass().getName());
            return null;
        }

        @Override
        public <T> void afterCompletion(NativeWebRequest request, Callable<T> task) throws Exception {
            log.error("任务正常结束:{}", task.getClass().getName());
        }
    }

    static class AsyncHandlerInterceptor implements org.springframework.web.servlet.AsyncHandlerInterceptor {
        @Override
        public void afterConcurrentHandlingStarted(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        }

        @Override
        public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
            return false;
        }

        @Override
        public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        }

        @Override
        public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        }
    }
}