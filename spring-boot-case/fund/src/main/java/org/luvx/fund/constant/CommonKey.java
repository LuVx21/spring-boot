package org.luvx.fund.constant;

import com.google.common.util.concurrent.RateLimiter;

public interface CommonKey {
    @SuppressWarnings("UnstableApiUsage")
    RateLimiter rateLimiter = RateLimiter.create(10);

    String KEY_CODE = "fS_code";
    String KEY_NAME = "fS_name";
    String KEY_YEAR = "syl_1n";
    String KEY_MONTH_6 = "syl_6y";
    String KEY_MONTH_3 = "syl_3y";
    String KEY_MONTH_1 = "syl_1y";
    String KEY_WORTH = "Data_ACWorthTrend";
    String KEY_GRAND = "Data_grandTotal";

    String URL_PREFIX = "http://fund.eastmoney.com/pingzhongdata/%s.js";
    String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) "
            + "Chrome/81.0.4044.122 Safari/537.36";
    String postJs = """
            function data() {
                return JSON.stringify({
                    "fS_code": fS_code,
                    "fS_name": fS_name,
                    "syl_1n": syl_1n,
                    "syl_6y": syl_6y,
                    "syl_3y": syl_3y,
                    "syl_1y": syl_1y,
                    "Data_ACWorthTrend": Data_ACWorthTrend,
                    "Data_grandTotal": Data_grandTotal[0].data,
                })
            };
            """;
}
