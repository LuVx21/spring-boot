package org.luvx.tools.hybrid.retrofit;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.luvx.tools.BaseAppTests;

class GithubApiTest extends BaseAppTests {
    @Resource
    private GithubApi githubApi;

    @Test
    void m1() throws Exception {
        CompletableFuture<Map<String, Object>> users = githubApi.users("LuVx");
        // System.out.println(users.get());

        // CompletableFuture<List<Object>> repos = githubApi.repos("LuVx");
        // System.out.println(repos.get());
    }
}