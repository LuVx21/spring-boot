package org.luvx.boot.tools.service.api;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.alibaba.fastjson2.JSONObject;

import org.junit.jupiter.api.Test;
import org.luvx.boot.tools.service.api.BiliApi;
import org.luvx.boot.tools.service.api.GithubApi;
import org.luvx.boot.tools.service.api.WeiboApi;
import org.luvx.boot.tools.service.retrofit.WeiboService;
import org.luvx.boot.tools.BaseAppTests;

class ApiTest extends BaseAppTests {
    @Resource
    private WeiboApi     weiboApi;
    @Resource
    private WeiboService weiboService;
    @Resource
    private BiliApi      biliApi;
    @Resource
    private GithubApi    githubApi;

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