package org.luvx.tools.web.base.validate.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class SexConstraintValidator implements ConstraintValidator<Sex, String> {
    /**
     * 性别约束逻辑
     * 如果value为null,那么该校验规则不生效;
     * 可搭配@NotNull注解使用,更加灵活
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        return StringUtils.equalsAny(value, "男", "女");
    }
}