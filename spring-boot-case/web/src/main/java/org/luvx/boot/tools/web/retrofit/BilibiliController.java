
package org.luvx.boot.tools.web.retrofit;

import org.luvx.boot.tools.service.retrofit.BilibiliService;
import org.luvx.boot.web.response.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/bili")
public class BilibiliController {

    @Resource
    private BilibiliService service;

    @GetMapping(value = "pull/season")
    public R<Object> rss(@RequestParam(value = "seasonId") Long seasonId) {
        if (seasonId == null || seasonId <= 0) {
            return R.fail();
        }
        List<String> result = service.pullSeasonList(seasonId);
        return R.success(result);
    }
}
