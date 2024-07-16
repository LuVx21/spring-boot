
package org.luvx.boot.mars.web.retrofit;

import org.luvx.boot.mars.service.api.GithubApi;
import org.luvx.boot.web.response.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/retrofit")
public class RetrofitController {
    @Resource
    private GithubApi githubApi;

    @GetMapping(value = "/github")
    public R<Object> github() throws ExecutionException, InterruptedException {
        githubApi.users("LuVx");
        githubApi.users("LuVx");
        CompletableFuture<Map<String, Object>> users = githubApi.users("LuVx");
        return R.success(users.get());
    }
}
