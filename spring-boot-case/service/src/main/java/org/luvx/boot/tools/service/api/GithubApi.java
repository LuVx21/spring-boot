package org.luvx.boot.tools.service.api;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import org.luvx.boot.tools.service.api.interceptor.TimeStampInterceptor;
import org.luvx.boot.tools.service.api.interceptor.common.RateLimiterInterceptor;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RetrofitClient(baseUrl = "${api.baseUrl.github}")
@Intercept(handler = TimeStampInterceptor.class)
@Intercept(handler = RateLimiterInterceptor.class)
public interface GithubApi {
    @GET("users/{userName}")
    CompletableFuture<Map<String, Object>> users(@Path("userName") String userName);

    @GET("users/{userName}/repos")
    String repos(@Path("userName") String userName);
}