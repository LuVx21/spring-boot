
package org.luvx.boot.tools.web.retrofit;

import jakarta.annotation.Resource;
import org.luvx.boot.tools.service.retrofit.WeiboService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weibo")
public class WeiboController {

    @Resource
    private WeiboService weiboService;

    @GetMapping(value = "rss", produces = "application/rss+xml;charset=UTF-8")
    public String rss() {
        return weiboService.rss();
    }
}
