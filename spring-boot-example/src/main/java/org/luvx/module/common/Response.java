package org.luvx.module.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Response<T> extends com.baomidou.mybatisplus.extension.api.R<T> implements Serializable {
    private static final long serialVersionUID = -4577255781088498763L;

    private static final int OK           = 0;
    private static final int FAIL         = 1;
    private static final int UNAUTHORIZED = 2;

    // public static R isOk() {
    //     return new R();
    // }
    //
    // public static R isFail() {
    //     return new R().code(FAIL);
    // }
    //
    // public static R isFail(Throwable e) {
    //     return isFail().msg(e);
    // }
    //
    // public R code(int code) {
    //     this.setCode(code);
    //     return this;
    // }
    //
    // public R msg(Throwable e) {
    //     this.setMsg(e.toString());
    //     return this;
    // }
    //
    // public R data(T data) {
    //     this.setData(data);
    //     return this;
    // }
}
