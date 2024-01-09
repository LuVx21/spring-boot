package org.luvx.boot.tools.service.api;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.luvx.boot.tools.service.api.interceptor.TimeStampInterceptor;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;

import retrofit2.http.GET;
import retrofit2.http.Path;

@RetrofitClient(baseUrl = "${api.baseUrl.github}")
@Intercept(handler = TimeStampInterceptor.class)
public interface GithubApi {
    @GET("users/{userName}")
    CompletableFuture<Map<String, Object>> users(@Path("userName") String userName);

    @GET("users/{userName}/repos")
    CompletableFuture<List<Object>> repos(@Path("userName") String userName);
}