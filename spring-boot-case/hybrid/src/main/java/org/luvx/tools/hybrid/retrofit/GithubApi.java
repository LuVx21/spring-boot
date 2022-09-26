package org.luvx.tools.hybrid.retrofit;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;

import retrofit2.http.GET;
import retrofit2.http.Path;

@RetrofitClient(baseUrl = "${api.baseUrl.github}")
public interface GithubApi {
    @GET("users/{userName}")
    CompletableFuture<Map<String, Object>> users(@Path("userName") String userName);

    @GET("users/{userName}/repos")
    CompletableFuture<List<Object>> repos(@Path("userName") String userName);
}