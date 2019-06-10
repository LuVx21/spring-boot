package org.luvx.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: org.luvx.common.annotations
 * @Description: 表的主键id, 仅支持唯一主键
 * @Author: Ren, Xie
 * @Date: 2019/5/27 20:18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableId {
    String value() default "id";
}
