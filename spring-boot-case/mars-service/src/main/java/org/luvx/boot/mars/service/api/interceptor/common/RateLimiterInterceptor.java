package org.luvx.boot.mars.service.api.interceptor.common;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import org.luvx.coding.common.consts.Commons;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RateLimiterInterceptor extends BasePathMatchInterceptor {

    @Override
    public Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        RateLimiter limiter = Commons.getLimiter(url.host(), url.port());
        log.info("启用限流...rate:{}", limiter.getRate());
        limiter.acquire();
        return chain.proceed(request);
    }
}
