package org.luvx.tools.web.base.validate.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SexConstraintValidator.class)
public @interface Sex {

    String message() default "性别参数有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}