package org.luvx.common.throwable.base;

/**
 * @author: Ren, Xie
 * @desc:
 */
public interface BaseAssert {
    String getCode();

    String getMessage();

    BaseException newException(Object... args);

    BaseException newException(Throwable t, Object... args);

    default void assertNotNull(Object obj) {
        if (obj == null) {
            throw newException();
        }
    }

    default void assertNotNull(Object obj, Object... args) {
        if (obj == null) {
            throw newException(args);
        }
    }
}
