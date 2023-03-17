package org.luvx.tools.web.shorturl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.common.exception.BizException;
import org.luvx.boot.web.response.R;
import org.luvx.boot.tools.service.shorturl.service.ShortenUrlService;
import org.luvx.tools.web.shorturl.entity.request.GetLongUrlReq;
import org.luvx.tools.web.shorturl.entity.request.GetShortUrlReq;
import org.luvx.tools.web.shorturl.entity.response.GetLongUrlResp;
import org.luvx.tools.web.shorturl.entity.response.GetShortUrlResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shortenUrl")
@Api(tags = "获取长域名服务")
@Slf4j
@Validated
public class ShortenUrlController {

    @Autowired
    private ShortenUrlService shortenUrlService;

    @GetMapping("test")
    public R<String> a() {
        return R.success("a");
    }

    @PostMapping("getShortUrl")
    @ApiOperation("获取APP信息")
    public R<GetShortUrlResp> getShortUrl(@RequestBody @Validated @ApiParam GetShortUrlReq req) {
        GetShortUrlResp resp = new GetShortUrlResp(shortenUrlService.getShortUrl(req.getUrl()));
        return R.success(resp);
    }

    @PostMapping("getLongUrl")
    @ApiOperation("获取APP信息")
    public R<GetLongUrlResp> getLongUrl(@RequestBody @Validated @ApiParam GetLongUrlReq req) {
        GetLongUrlResp resp = new GetLongUrlResp(shortenUrlService.getLongUrl(req.getShortUrl()));
        return R.success(resp);
    }

    public static R processException(Exception e) {
        log.error("处理过程出现错误", e);
        if (e instanceof BizException) {
            return R.fail(((BizException) e).getMsg(), null);
        }
        return R.fail();
    }
}
