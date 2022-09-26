package org.luvx.tools.hybrid.retrofit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.luvx.tools.BaseAppTests;
import org.springframework.beans.factory.annotation.Autowired;

class GithubApiTest extends BaseAppTests {
    @Autowired
    private GithubApi githubApi;

    @Test
    void m1() throws Exception {
        CompletableFuture<Map<String, Object>> users = githubApi.users("LuVx");
        // System.out.println(users.get());

        CompletableFuture<List<Object>> repos = githubApi.repos("LuVx");
        // System.out.println(repos.get());
    }
}