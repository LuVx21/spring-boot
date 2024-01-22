package org.luvx.boot.tools.service.shorturl.service.impl;

import jakarta.annotation.Resource;
import org.luvx.boot.tools.service.shorturl.converter.BaseDomainProvider;
import org.luvx.boot.tools.service.shorturl.service.ShortenUrlService;
import org.luvx.coding.common.util.Asserts;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlServiceImpl implements ShortenUrlService {
    @Resource
    private BaseDomainProvider domainProvider;

    @Override
    public String getShortUrl(String longUrl) {
        Asserts.checkNotBlank(longUrl, "非法请求参数");
        return domainProvider.getShortUrl(longUrl);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        Asserts.checkNotBlank(shortUrl, "非法请求参数");
        return domainProvider.getLongUrl(shortUrl);
    }
}
