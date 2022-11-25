package org.luvx.tools.service.retrofit;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.luvx.tools.BaseAppTests;
import org.luvx.tools.service.retrofit.GithubApi;

class GithubApiTest extends BaseAppTests {
    @Resource
    private GithubApi githubApi;

    @Test
    void m1() throws Exception {
        CompletableFuture<Map<String, Object>> users = githubApi.users("LuVx");
        System.out.println(users.get());

        // CompletableFuture<List<Object>> repos = githubApi.repos("LuVx");
        // System.out.println(repos.get());
    }
}