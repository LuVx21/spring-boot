
package org.luvx.boot.mars.web.retrofit;

import org.luvx.boot.mars.service.retrofit.BilibiliService;
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
        if (seasonId == null || seasonId < 0) {
            return R.fail();
        }
        if (seasonId == 0) {
            service.pullAll();
            return R.success("all ok!");
        }
        List<String> result = service.pullSeasonList(seasonId);
        return R.success(result);
    }
}
