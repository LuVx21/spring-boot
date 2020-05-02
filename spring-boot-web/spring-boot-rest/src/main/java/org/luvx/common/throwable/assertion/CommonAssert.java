package org.luvx.common.throwable.assertion;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.luvx.common.throwable.base.BusinessExceptionAssert;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Getter
@AllArgsConstructor
public enum CommonAssert implements BusinessExceptionAssert {
    SUCCESS("00000", "成功"),
    FAILED("99999", "失败"),
    SERVER_ERROR("1", "");
    /**
     * 返回码
     */
    private String code;
    /**
     * 返回消息
     */
    private String message;
}
