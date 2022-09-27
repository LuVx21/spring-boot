package org.luvx.tools.hybrid.interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * {@code @Intercept}
 */
@Component
public class TimeStampInterceptor extends BasePathMatchInterceptor {
    @Override
    public Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        long timestamp = System.currentTimeMillis();
        HttpUrl newUrl = url.newBuilder()
                .addQueryParameter("timestamp", String.valueOf(timestamp))
                .build();
        Request newRequest = request.newBuilder()
                .url(newUrl)
                .build();
        return chain.proceed(newRequest);
    }
}
