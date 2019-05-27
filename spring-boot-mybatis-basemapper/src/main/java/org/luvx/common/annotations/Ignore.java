package org.luvx.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: org.luvx.common.annotations
 * @Description: entity中getter,setter方法
 * @Author: Ren, Xie
 * @Date: 2019/5/27 17:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Ignore {

}