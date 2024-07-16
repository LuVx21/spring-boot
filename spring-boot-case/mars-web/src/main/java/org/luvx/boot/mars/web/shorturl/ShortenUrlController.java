package org.luvx.boot.mars.web.shorturl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mars.service.shorturl.service.ShortenUrlService;
import org.luvx.boot.mars.web.shorturl.entity.request.GetLongUrlReq;
import org.luvx.boot.mars.web.shorturl.entity.request.GetShortUrlReq;
import org.luvx.boot.mars.web.shorturl.entity.response.GetLongUrlResp;
import org.luvx.boot.mars.web.shorturl.entity.response.GetShortUrlResp;
import org.luvx.boot.web.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shortenUrl")
@Api(tags = "获取长域名服务")
@Slf4j
@Validated
public class ShortenUrlController {

    @Autowired
    private ShortenUrlService shortenUrlService;

    @PostMapping("getShortUrl")
    @ApiOperation("获取APP信息")
    public R<GetShortUrlResp> getShortUrl(@RequestBody @Validated @ApiParam GetShortUrlReq req) {
        String shortUrl = shortenUrlService.getShortUrl(req.getUrl());
        return R.success(new GetShortUrlResp(shortUrl));
    }

    @PostMapping("getLongUrl")
    @ApiOperation("获取APP信息")
    public R<GetLongUrlResp> getLongUrl(@RequestBody @Validated @ApiParam GetLongUrlReq req) {
        String longUrl = shortenUrlService.getLongUrl(req.getShortUrl());
        return R.success(new GetLongUrlResp(longUrl));
    }

}
