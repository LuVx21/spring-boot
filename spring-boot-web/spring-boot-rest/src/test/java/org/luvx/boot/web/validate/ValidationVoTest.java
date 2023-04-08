package org.luvx.boot.web.validate;

import jakarta.validation.*;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.Test;
import org.luvx.boot.web.entity.validate.ValidationVo;

@Slf4j
class ValidationVoTest {
    private static final ValidatorFactory VALIDATOR_FACTORY = Validation
            .byProvider(HibernateValidator.class)
            .configure()
            .failFast(false)
            .buildValidatorFactory();
    Validator validator = VALIDATOR_FACTORY.getValidator();
    // Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void m1() {
        String json = """
                {
                  "username": "name",
                  "password": "1234",
                  "email": "123456@qq.com",
                  "birthday": "2019-04-14T09:05:39.604Z",
                  "age": 20,
                  "sex": 0,
                  "sexName": "男",
                  "hobbies":["abc"]
                  "voList": [
                    {
                      "id": 10,
                      "vipLevel": 5
                    }
                  ]
                }
                """;
        ValidationVo vo = JSON.parseObject(json, ValidationVo.class);

        log.info("新增操作检查:");
        printMsg(validator.validate(vo));

        // log.info("更新操作检查:");
        // vo.setId(1);
        // printMsg(validator.validate(vo));
    }

    private void printMsg(Set<ConstraintViolation<ValidationVo>> result) {
        String collect = result.stream()
                .map(r -> r.getPropertyPath() + ": " + r.getMessage() + ": " + r.getInvalidValue())
                .collect(Collectors.joining("\n"));
        System.out.println("结果:\n" + collect);
    }
}