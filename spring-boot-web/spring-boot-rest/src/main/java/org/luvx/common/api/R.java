package org.luvx.common.api;

import lombok.Setter;
import org.luvx.common.throwable.base.BaseAssert;
import org.luvx.common.throwable.assertion.CommonAssert;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Setter
public class R<T> {
    String code;
    String msg;
    T      data;

    public R() {
    }

    public R(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CommonAssert.SUCCESS);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, CommonAssert.FAILED.getMessage(), msg);
    }

    public static R failed(String code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> failed(BaseAssert errorCode) {
        return restResult(null, errorCode.getCode(), errorCode.getMessage());
    }

    public static <T> R<T> restResult(T data, BaseAssert b) {
        return restResult(data, b.getCode(), b.getMessage());
    }

    private static <T> R<T> restResult(T data, String code, String msg) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }
}
