package org.luvx.boot.tools.service.shorturl.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.luvx.boot.common.exception.BizException;
import org.luvx.boot.tools.service.shorturl.converter.BaseDomainProvider;
import org.luvx.boot.tools.service.shorturl.service.ShortenUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlServiceImpl implements ShortenUrlService {
    @Autowired
    private BaseDomainProvider domainProvider;

    @Override
    public String getShortUrl(String longUrl) {
        if (StringUtils.isBlank(longUrl)) {
            throw new BizException("非法请求参数");
        }
        return domainProvider.getShortUrl(longUrl);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        if (StringUtils.isBlank(shortUrl)) {
            throw new BizException("非法请求参数");
        }
        return domainProvider.getLongUrl(shortUrl);
    }
}
