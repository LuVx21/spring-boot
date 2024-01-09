package org.luvx.boot.web.entity.validate;

import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.Default;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.group.GroupSequenceProvider;
import org.luvx.boot.web.entity.validate.custom.Sex;
import org.luvx.boot.web.entity.validate.custom.VoProvider;

@Getter
@Setter
@ToString
@GroupSequenceProvider(VoProvider.class)
public class ValidationVo {
    @NotNull(message = "必须指定id", groups = {UpdateGroup.class})
    private Integer        id;
    @NotBlank(message = "用户名不可为blank", groups = {AddGroup.class})
    // @NotEmpty(message = "用户名不可为empty", groups = {AddGroup.class})
    // @NotNull(message = "用户名不可为null", groups = {AddGroup.class})
    // @Length(min = 4, max = 8, message = "用户名长度要求在{min}-{max}之间")
    private String         username;
    @NotNull(message = "密码不可为null", groups = {AddGroup.class})
    @Null(message = "密码不可修改", groups = {UpdateGroup.class})
    private String         password;
    @Email(message = "邮箱格式错误", groups = {AddGroup.class, UpdateGroup.class})
    private String         email;
    @Past(message = "出生日期错误")
    private Date           birthday;
    @Min(value = 18, message = "未成年不满足注册要求")
    @Max(value = 80, message = "年龄错误")
    private Integer        age;
    @Range(min = 0, max = 1, message = "性别选择错误")
    private Integer        sex;
    @Sex(message = "只能为男/女")
    private String         sexName;
    @Valid
    // @NotNull(message = "voList不可为空")
    @NotEmpty(message = "voList必须有元素")
    private List<ObjectVO> voList;


    //region 多字段联合校验
    @Size(min = 1, max = 3, message = "爱好名字范围在3~5")
    @NotNull(groups = {MaleGroup.class, FemaleGroup.class}, message = "指定性别时需给出爱好")
    private List<String> hobbies;
    //endregion

    //region 控制校验顺序
    @GroupSequence({Default.class, AddGroup.class, UpdateGroup.class, MaleGroup.class, FemaleGroup.class})
    public interface Group {
    }
    //region


    @Getter
    @Setter
    @ToString
    public static class ObjectVO {
        private Integer id;
        @Min(1)
        @Max(value = 5, message = "vip等级最多为5")
        private Integer vipLevel;
    }

    public interface AddGroup extends Default {
    }

    public interface UpdateGroup extends Default {
    }

    public interface MaleGroup extends Default {
    }

    public interface FemaleGroup extends Default {
    }
}