package org.luvx.boot.mul.mybatis.config;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DS {
    String value() default "";
}