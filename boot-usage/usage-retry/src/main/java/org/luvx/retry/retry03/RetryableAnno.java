package org.luvx.retry.retry03;

import java.lang.annotation.*;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RetryableAnno {
    int maxAttempts() default 3;

    Class<? extends Throwable> value() default RuntimeException.class;
}
