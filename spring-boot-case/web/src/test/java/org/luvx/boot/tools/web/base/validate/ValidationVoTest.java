package org.luvx.boot.tools.web.base.validate;

import com.alibaba.fastjson2.JSON;
import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

class ValidationVoTest {
    private static final ValidatorFactory VALIDATOR_FACTORY = Validation
            .byProvider(HibernateValidator.class)
            .configure()
            .failFast(false)
            .buildValidatorFactory();
    Validator validator = VALIDATOR_FACTORY.getValidator();

    @Test
    public void m1() {
        String json = """
                {
                  "username": "",
                  "password": "1234",
                  "email": "123456@qq.com",
                  "birthday": "2019-04-14T09:05:39.604Z",
                  "age": 20,
                  "sex": 0,
                  "sexName": "男",
                  "voList": [
                    {
                      "id": 10,
                      "vipLevel": 6
                    }
                  ]
                }
                """;
        ValidationVo vo = JSON.parseObject(json, ValidationVo.class);

        extracted(vo);

        vo.setId(1);

        extracted(vo);
    }

    private void extracted(ValidationVo vo) {
        Set<ConstraintViolation<ValidationVo>> result = validator.validate(vo);
        String collect = result.stream()
                .map(r -> r.getPropertyPath() + ": " + r.getMessage())
                .collect(Collectors.joining("\n"));
        System.out.println("结果:\n" + collect);
    }
}