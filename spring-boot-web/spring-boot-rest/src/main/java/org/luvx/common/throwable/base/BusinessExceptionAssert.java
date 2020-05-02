package org.luvx.common.throwable.base;

import org.luvx.common.throwable.exception.BusinessException;

import java.text.MessageFormat;

/**
 * @author: Ren, Xie
 * @desc:
 */
public interface BusinessExceptionAssert extends BaseAssert {
    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, msg, args);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, msg, t, args);
    }
}