package org.luvx.common.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: org.luvx.common.annotation
 * @Description: 方法执行时间计测
 * @Author: Ren, Xie
 * @Date: 2019/3/8 16:17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MeasurementAnnotation {
}