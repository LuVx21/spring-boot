package org.luvx.tools.web.base.validate;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.luvx.tools.web.base.validate.ValidationVo.AddGroup;
import org.luvx.tools.web.base.validate.ValidationVo.UpdateGroup;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.vavr.Tuple;

/**
 * <pre>
 * @Valid 和 @Validated 的区别:
 * 1. 包
 * 2.
 * </pre>
 */
@RestController
public class ValidationController {
    @PostMapping("save")
    public Tuple save(@Validated({AddGroup.class}) @RequestBody @NotNull ValidationVo demo, BindingResult result) {
        if (result.hasErrors()) {
            List<String> collect = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return Tuple.of("校验失败", collect);
        }
        return Tuple.of("校验成功");
    }

    @PostMapping("update")
    public Tuple update(@Validated({UpdateGroup.class}) @RequestBody @NotNull ValidationVo demo,
            BindingResult result) {
        if (result.hasErrors()) {
            List<String> collect = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return Tuple.of("校验失败", collect);
        }
        return Tuple.of("校验成功");
    }
}
