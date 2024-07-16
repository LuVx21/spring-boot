
package org.luvx.boot.mars.web.retrofit;

import jakarta.annotation.Resource;
import org.luvx.boot.mars.service.retrofit.WeiboService;
import org.luvx.boot.web.response.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weibo")
public class WeiboController {

    @Resource
    private WeiboService weiboService;

    @GetMapping(value = "rss", produces = "application/xml;charset=UTF-8")
    public String rss() {
        return weiboService.rss();
    }

    @GetMapping(value = "rss/delete/{id}")
    public R<Boolean> rssDelete(@PathVariable("id") Long id) {
        return R.success(weiboService.deleteById(id));
    }
}
