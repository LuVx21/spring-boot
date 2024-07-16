package org.luvx.boot.mars.service.api;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.luvx.boot.mars.service.api.GithubApi;
import org.luvx.boot.mars.service.api.WeiboApi;
import org.luvx.boot.mars.BaseAppTests;
import org.luvx.boot.mars.service.retrofit.WeiboService;

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
        String cookie = "";
        String json = weiboApi.byGroup(
                cookie,
                4670120389774996L, 4, 1, 25, Map.of("max_id", "4988660693075906")
        );
        System.out.println(json);
    }
}