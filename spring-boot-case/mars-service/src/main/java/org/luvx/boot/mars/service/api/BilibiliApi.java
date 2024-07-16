package org.luvx.boot.mars.service.api;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import org.luvx.boot.mars.service.api.interceptor.common.RateLimiterInterceptor;
import org.luvx.boot.mars.service.api.interceptor.common.UserAgentInterceptor;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Intercept(handler = RateLimiterInterceptor.class)
@Intercept(handler = UserAgentInterceptor.class)
@RetrofitClient(baseUrl = "https://api.bilibili.com")
public interface BilibiliApi {
    @GET("x/polymer/web-space/seasons_series_list")
    String seasonsSeriesList(
            @Query("mid") String mid,
            @Query("page_num") String page_num,
            @Query("page_size") int page_size,
            @Query("web_location") String web_location,
            @Query("w_rid") String w_rid,
            @Query("wts") String wts
    );

    @GET("x/space/fav/season/list")
    String seasonList(
            @Query("season_id") long season_id,
            @Query("pn") int pn,
            @Query("ps") int ps
    );
}
