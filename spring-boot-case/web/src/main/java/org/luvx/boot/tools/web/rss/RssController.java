
package org.luvx.boot.tools.web.rss;

import jakarta.annotation.Resource;
import org.luvx.boot.tools.service.rss.RssService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rss")
public class RssController {
    @Resource
    private RssService rssService;

    @GetMapping(value = "/{key}/feed", produces = "application/rss+xml;charset=UTF-8")
    public String rss(@PathVariable("key") String key) {
        return rssService.rss(key);
    }
}
