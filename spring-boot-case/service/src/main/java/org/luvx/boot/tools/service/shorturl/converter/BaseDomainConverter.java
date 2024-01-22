package org.luvx.boot.tools.service.shorturl.converter;

/**
 * 长域名转换为短域名的实现方案也有多种方案: 62 进制递增, 随机生成等
 * 因此使用抽象方式实现
 */
public abstract class BaseDomainConverter {
    /**
     * 严格来说这里不需要长域名作为参数
     * 因为短域名不是通过计算得到, 同一个长连接可能得到不同的结果,长域名对短域名的产生不应该有影响
     */
    public abstract String shorten(String longUrl);
}
