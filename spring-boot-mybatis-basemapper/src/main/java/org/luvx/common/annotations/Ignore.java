package org.luvx.common.annotations;

import java.lang.annotation.*;

/**
 * @ClassName: org.luvx.common.annotations
 * @Description: entity中getter, setter方法
 * @Author: Ren, Xie
 * @Date: 2019/5/27 17:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Inherited
public @interface Ignore {
}
