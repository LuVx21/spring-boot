package org.luvx.tools.service.retrofit;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.alibaba.fastjson2.JSONObject;

import org.junit.jupiter.api.Test;
import org.luvx.tools.BaseAppTests;
import org.luvx.tools.service.retrofit.bili.BiliApi;

class GithubApiTest extends BaseAppTests {

    @Resource
    private BiliApi   biliApi;
    @Resource
    private GithubApi githubApi;

    @Test
    void m1() throws Exception {
        CompletableFuture<Map<String, Object>> users = githubApi.users("LuVx");
        System.out.println(users.get());

        // CompletableFuture<List<Object>> repos = githubApi.repos("LuVx");
        // System.out.println(repos.get());
    }

    @Test
    void m2() {
        JSONObject pubdate = biliApi.getVideoList(21837784L, 1, 30, "pubdate");
        System.out.println(pubdate);
    }
}