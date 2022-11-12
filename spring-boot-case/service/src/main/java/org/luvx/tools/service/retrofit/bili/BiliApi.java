package org.luvx.tools.service.retrofit.bili;

import com.alibaba.fastjson2.JSONObject;
import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;

import retrofit2.http.GET;
import retrofit2.http.Query;

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
