package org.luvx.tools.service.retrofit.interceptor;

import java.io.IOException;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import static com.google.common.net.HttpHeaders.COOKIE;
import static com.google.common.net.HttpHeaders.REFERER;

@Slf4j
@Component
public class GeekTimeInterceptor extends BasePathMatchInterceptor {
    @Setter
    private static String cookie = "";

    @Override
    public Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .header(COOKIE, cookie)
                .header(REFERER, "https://time.geekbang.org")
                .build();
        return chain.proceed(newRequest);
    }
}
