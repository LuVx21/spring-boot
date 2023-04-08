package org.luvx.boot.web.entity.validate.custom;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SexConstraintValidator.class)
public @interface Sex {

    String message() default "性别参数有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}