package org.luvx.common.enums;

/**
 * @ClassName: org.luvx.common.enums
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/22 19:52
 */
public enum StatusCode {
    /**
     * OK
     */
    OK("OK", 0),
    /**
     * FAIL
     */
    FAIL("FAIL", 1),
    /**
     * UNAUTHORIZED
     */
    UNAUTHORIZED("UNAUTHORIZED", 2);

    StatusCode(String str, int i) {
    }
}