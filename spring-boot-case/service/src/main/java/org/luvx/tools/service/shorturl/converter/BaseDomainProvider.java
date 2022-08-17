package org.luvx.tools.service.shorturl.converter;

/**
 * 域名存储实现可有多种方案: 如字典树, map 接口
 * 因此提供抽象实现
 */
public interface BaseDomainProvider {
    String getLongUrl(String shortUrl);

    String getShortUrl(String longUrl);
}
