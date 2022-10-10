package org.luvx.tools.hybrid.retrofit.interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;

import lombok.Setter;
import okhttp3.Request;
import okhttp3.Response;

/**
 * {@code @Sign(accessKeyId = "${api.accessKeyId}", accessKeySecret = "${api.accessKeySecret}", exclude = {"/api
 * /users"})}
 */
@Component
public class SignInterceptor extends BasePathMatchInterceptor {
    @Setter
    private String accessKeyId;
    @Setter
    private String accessKeySecret;

    @Override
    public Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newReq = request.newBuilder()
                .addHeader("accessKeyId", accessKeyId)
                .addHeader("accessKeySecret", accessKeySecret)
                .build();
        return chain.proceed(newReq);
    }
}