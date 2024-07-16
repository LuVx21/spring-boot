package org.luvx.boot.mars.service.api.interceptor.common;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.google.common.net.HttpHeaders.COOKIE;

@Slf4j
@Component
public class CookieInterceptor extends BasePathMatchInterceptor {
    @Setter
    private static String cookie = "";

    @Override
    public Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .header(COOKIE, cookie)
                .build();
        return chain.proceed(newRequest);
    }
}
