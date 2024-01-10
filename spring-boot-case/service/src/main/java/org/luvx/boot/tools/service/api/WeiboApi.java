package org.luvx.boot.tools.service.api;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import com.google.common.net.HttpHeaders;
import org.luvx.boot.tools.service.api.interceptor.common.UserAgentInterceptor;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.util.Map;

@Intercept(handler = UserAgentInterceptor.class)
@RetrofitClient(baseUrl = "https://weibo.com")
public interface WeiboApi {
    @GET("ajax/statuses/hot_band")
    String hotBand();

    @GET("ajax/feed/groupstimeline")
    String byGroup(
            @Header(HttpHeaders.COOKIE) String cookie,
            @Query("list_id") long list_id,
            @Query("refresh") int refresh,
            @Query("fast_refresh") int fast_refresh,
            @Query("count") int count,
            @QueryMap Map<String, Object> filters);
}
