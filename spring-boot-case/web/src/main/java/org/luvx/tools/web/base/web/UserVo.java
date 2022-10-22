package org.luvx.tools.web.base.web;

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
    private String  passWord;
    @JsonProperty(access = Access.WRITE_ONLY)
    private Integer age;
    @EnumNameAnno(CommonStatusEnum.class)
    @JsonSerialize(using = EnumNameSerializer.class)
    private int     valid;

    public String getTest() {
        return userName + ":" + passWord;
    }
}