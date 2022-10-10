package org.luvx.tools.hybrid.retrofit.interceptor;

import static com.google.common.net.HttpHeaders.COOKIE;
import static com.google.common.net.HttpHeaders.REFERER;
import static com.google.common.net.HttpHeaders.USER_AGENT;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
@Component
public class GeekTimeInterceptor extends BasePathMatchInterceptor {
    private final String cookie = "";
    private final String value  =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 "
                    + "Safari/537.36";

    @Override
    public Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .header(COOKIE, cookie)
                .header(REFERER, "https://time.geekbang.org")
                .header(USER_AGENT, value)
                .build();
        return chain.proceed(newRequest);
    }
}
