package org.luvx.tools.service.retrofit;

import java.util.Map;

import org.luvx.tools.service.retrofit.interceptor.GeekTimeInterceptor;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.luvx.tools.service.retrofit.interceptor.common.UserAgentInterceptor;
import retrofit2.http.Body;
import retrofit2.http.POST;

@Intercept(handler = GeekTimeInterceptor.class)
@Intercept(handler = UserAgentInterceptor.class)
@RetrofitClient(baseUrl = "https://time.geekbang.org")
public interface GeekTimeApi {
    @POST("serv/v3/learn/product")
    Map<String,Object> product(@Body Map<String, Object> param);

    @POST("serv/v1/column/articles")
    Map<String,Object> articles(@Body ArticlesBody body);

    @POST("serv/v1/column/intro")
    Map<String,Object> intro(@Body IntroBody body);

    @POST("serv/v1/article")
    Map<String,Object> article(@Body ArticleBody body);

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


}
