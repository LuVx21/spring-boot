package org.luvx.tools.service.retrofit.bili;

import com.alibaba.fastjson2.JSONObject;
import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;

import org.luvx.tools.service.retrofit.interceptor.common.UserAgentInterceptor;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Intercept(handler = UserAgentInterceptor.class)
@RetrofitClient(baseUrl = "https://api.bilibili.com")
public interface BiliApi {
    @GET("x/space/arc/search")
    JSONObject getVideoList(
            @Query("mid") long mid,
            @Query("pn") int pn,
            @Query("ps") int ps,
            @Query("order") String order
    );
}
