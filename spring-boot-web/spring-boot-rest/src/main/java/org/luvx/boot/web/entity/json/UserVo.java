package org.luvx.boot.web.entity.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.luvx.boot.web.enums.CommonStatusEnum;
import org.luvx.coding.common.enums.ext.EnumNameAnno;
import org.luvx.coding.common.enums.ext.EnumNameSerializer;

@Getter
@Setter
@ToString
public class UserVo {
    private Long    userId;
    private String  userName;
    private String  password;
    /**
     * @see Access#WRITE_ONLY 只接受传参,不作为响应参数
     * @see Access#READ_ONLY 不接受传参,只作为响应参数
     */
    @JsonProperty(access = Access.WRITE_ONLY)
    private Integer age;

    @EnumNameAnno(CommonStatusEnum.class)
    @JsonSerialize(using = EnumNameSerializer.class)
    private int              valid;
    @JsonInclude(Include.NON_DEFAULT)
    private Integer inValid;
    /**
     * 入出参都是直接使用枚举的 code
     */
    private CommonStatusEnum status;

    public String getTest() {
        return userName + ":" + password;
    }
}