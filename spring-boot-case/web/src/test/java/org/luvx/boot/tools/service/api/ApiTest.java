package org.luvx.boot.tools.service.api;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.luvx.boot.tools.BaseAppTests;
import org.luvx.boot.tools.service.retrofit.WeiboService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

class ApiTest extends BaseAppTests {
    @Resource
    private WeiboApi     weiboApi;
    @Resource
    private WeiboService weiboService;
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
    }

}