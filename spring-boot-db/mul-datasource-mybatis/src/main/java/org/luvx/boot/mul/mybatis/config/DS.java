package org.luvx.boot.mul.mybatis.config;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DS {
    DSType value() default DSType.ds1;

    enum DSType {
        ds1, ds2
    }
}