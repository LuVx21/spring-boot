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
public enum ArgumentAssert implements BusinessExceptionAssert {
    VALID_ERROR("1", "");

    /**
     * 返回码
     */
    private String code;
    /**
     * 返回消息
     */
    private String message;
}
