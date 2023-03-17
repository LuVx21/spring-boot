package org.luvx.boot.tools.service.api;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;

import org.luvx.boot.tools.service.api.interceptor.common.UserAgentInterceptor;
import retrofit2.http.GET;

@Intercept(handler = UserAgentInterceptor.class)
@RetrofitClient(baseUrl = "https://weibo.com")
public interface WeiboApi {
    @GET("ajax/statuses/hot_band")
    String hotBand();
}
