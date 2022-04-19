package org.luvx.validate;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ValidationVo {
    public interface AddGroup {
    }

    public interface UpdateGroup {
    }

    private Integer        id;
    @Length(min = 4, max = 8, message = "用户名长度要求在{min}-{max}之间")
    @NotNull(message = "用户名不可为空", groups = {AddGroup.class})
    private String         username;
    @Email(message = "邮箱格式错误", groups = {AddGroup.class, UpdateGroup.class})
    private String         email;
    @Past(message = "出生日期错误")
    private Date           birthday;
    @Min(value = 18, message = "未成年不满足注册要求")
    @Max(value = 80, message = "年龄错误")
    private Integer        age;
    @Range(min = 0, max = 1, message = "性别选择错误")
    private Integer        sex;
    @Valid
    @NotNull(message = "voList不可为空")
    private List<ObjectVO> voList;

    @Getter
    @Setter
    @ToString
    public static class ObjectVO {
        private Integer id;
        @Min(1)
        @Max(value = 5, message = "vip等级最多为5")
        private Integer vipLevel;
    }
}