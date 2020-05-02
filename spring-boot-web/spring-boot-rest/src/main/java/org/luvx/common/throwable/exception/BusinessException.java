package org.luvx.common.throwable.exception;


import org.luvx.common.throwable.base.BaseAssert;
import org.luvx.common.throwable.base.BaseException;

/**
 * @author: Ren, Xie
 * @desc:
 */
public class BusinessException extends BaseException {

    public BusinessException(BaseAssert r, String msg, Object[] args) {
        super(r, msg, args);
    }

    public BusinessException(BaseAssert r, String msg, Throwable cause, Object[] args) {
        super(r, msg, cause, args);
    }
}
