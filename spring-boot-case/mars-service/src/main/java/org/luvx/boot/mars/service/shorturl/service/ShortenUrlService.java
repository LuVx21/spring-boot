package org.luvx.boot.mars.service.shorturl.service;

/**
 * 短域名服务的逻辑处理层
 */
public interface ShortenUrlService {

    /**
     * 通过长域名获取短域名
     * @param longUrl
     * @return
     */
    String getShortUrl(String longUrl);

    /**
     * 通过短域名获取长域名
     * @param shortUrl
     * @return
     */
    String getLongUrl(String shortUrl);

}
