package org.luvx.validate;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.luvx.validate.ValidationVo.UpdateGroup;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.vavr.Tuple2;

@RestController
public class ValidationController {
    /**
     * <pre>
     *     {
     *         "username": "jacklinjacklin",
     *         "email": "793066408@qq.com",
     *         "birthday": "2019-04-14T09:05:39.604Z",
     *         "age": 22,
     *         "sex": 0,
     *         "voList": [
     *             {
     *                 "id": 10,
     *                 "vipLevel": 6
     *             }
     *         ]
     *     }
     * </pre>
     */
    @PostMapping("save")
    public List<Tuple2> save(@Valid @RequestBody @NotNull ValidationVo demo, BindingResult result) {
        if (result.hasErrors()) {
            return result.getAllErrors().stream()
                    .map(e -> new Tuple2("校验失败", e.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
        return List.of(new Tuple2("校验成功", ""));
    }

    @PostMapping("update")
    public List<Tuple2> update(@Validated({UpdateGroup.class}) @RequestBody @NotNull ValidationVo demo,
            BindingResult result) {
        if (result.hasErrors()) {
            return result.getAllErrors().stream()
                    .map(e -> new Tuple2("校验失败", e.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
        return List.of(new Tuple2("校验成功", ""));
    }
}
