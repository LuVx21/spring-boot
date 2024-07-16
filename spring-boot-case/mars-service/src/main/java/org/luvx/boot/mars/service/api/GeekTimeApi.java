package org.luvx.boot.mars.service.api;

import java.util.Map;

import org.luvx.boot.mars.service.api.interceptor.GeekTimeInterceptor;
import org.luvx.boot.mars.service.api.interceptor.common.RateLimiterInterceptor;
import org.luvx.boot.mars.service.api.interceptor.common.UserAgentInterceptor;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import retrofit2.http.Body;
import retrofit2.http.POST;

@Intercept(handler = GeekTimeInterceptor.class)
@Intercept(handler = UserAgentInterceptor.class)
@Intercept(handler = RateLimiterInterceptor.class)
@RetrofitClient(baseUrl = "https://time.geekbang.org")
public interface GeekTimeApi {
    @POST("serv/v3/learn/product")
    Map<String, Object> product(@Body Map<String, Object> param);

    @POST("serv/v1/column/articles")
    Map<String, Object> articles(@Body ArticlesBody body);

    @POST("serv/v1/column/intro")
    Map<String, Object> intro(@Body IntroBody body);

    @POST("serv/v1/article")
    String article(@Body ArticleBody body);

    @POST("serv/v1/comments")
    String comments(@Body CommentBody body);

    @Getter
    @Setter
    @ToString
    class ArticleBody {
        private String  id;
        private boolean include_neighbors = true;
        private boolean is_freelyread     = true;
    }

    @Getter
    @Setter
    @ToString
    class ArticlesBody {
        private long    cid;
        private int     size  = 300;
        private int     prev;
        private String  order = "earliest";
        private boolean sample;
    }

    @Getter
    @Setter
    @ToString
    class IntroBody {
        private String  cid;
        private boolean with_groupbuy;
    }

    @Getter
    @Setter
    @ToString
    class CommentBody {
        private String aid;
        private String prev = "0";
    }
}
