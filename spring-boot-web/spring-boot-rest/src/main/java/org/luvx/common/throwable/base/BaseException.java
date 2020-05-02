package org.luvx.common.throwable.base;

import lombok.Getter;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Getter
public class BaseException extends RuntimeException {
    BaseAssert baseAssert;
    Object[]   args;

    public BaseException(BaseAssert baseAssert, Object... args) {
        this.args = args;
        this.baseAssert = baseAssert;
    }

    public BaseException(BaseAssert baseAssert, String msg, Object... args) {
        super(msg);
        this.args = args;
        this.baseAssert = baseAssert;
    }

    public BaseException(BaseAssert baseAssert, String msg, Throwable cause, Object... args) {
        super(msg, cause);
        this.args = args;
        this.baseAssert = baseAssert;
    }
}
