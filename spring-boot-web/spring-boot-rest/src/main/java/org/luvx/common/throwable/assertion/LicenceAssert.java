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
public enum LicenceAssert implements BusinessExceptionAssert {
    /**
     * Bad licence type
     */
    BAD_LICENCE_TYPE("7001", "Bad licence type."),
    /**
     * Licence not found
     */
    LICENCE_NOT_FOUND("7002", "Licence not found.");

    /**
     * 返回码
     */
    private String code;
    /**
     * 返回消息
     */
    private String message;
}