package org.luvx.boot.tools.service.shorturl.converter;

import org.luvx.coding.common.exception.BizException;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 直接把长信息交给scissor去处理的调度者，没有特殊处理
 */
@Service
public class SimpleDomainConverter extends BaseDomainConverter {

    // 短url中可能出现的字符（a-z，A-Z，0-9），排序是打乱的
    private static final char[] DIGITS               = "TjAuBevr5SFi2R9UDLcsMWhtG7dgCn0kKNxZIaYo63Qql8fwJPbm1Hzy4pVOXE".toCharArray();
    private static final int    MAX_SHORT_URL_LENGTH = 8;
    /**
     * 最多8个字符，62进制8个字符表示的最大值
     */
    private static final long   MAX_NUM              = (long) Math.pow(DIGITS.length, MAX_SHORT_URL_LENGTH) - 1;

    /**
     * 62进制中的3个字符表示的最小值减去1，因为生成的短字符串最小3位
     */
    protected AtomicLong num = new AtomicLong((long) Math.pow(DIGITS.length, 2) - 1);
    // 起始8位
    // protected AtomicLong num = new AtomicLong(3521614606207L);

    @Override
    public String shorten(String longUrl) {
        long generateNum = num.incrementAndGet();
        if (generateNum > MAX_NUM || generateNum == Long.MAX_VALUE) {
            throw new BizException("本服务已经无法提供更多短链接，请联系服务提供方");
        }
        return toOtherBaseString(generateNum, DIGITS.length);
    }

    /**
     * 将十进制的数字转换为指定进制的字符串
     *
     * @param n    十进制的数字
     * @param base 指定的进制
     */
    public String toOtherBaseString(long n, int base) {
        long num = n < 0 ? ((2L * 0x7fffffff) + n + 2) : n;
        char[] buf = new char[32];
        int charPos = 32;
        while ((num / base) > 0) {
            buf[--charPos] = DIGITS[(int) (num % base)];
            num /= base;
        }
        buf[--charPos] = DIGITS[(int) (num % base)];
        return new String(buf, charPos, (32 - charPos));
    }
}
