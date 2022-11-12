package org.luvx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import org.luvx.coding.common.enums.EnumHasCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BizTypeEnum implements EnumHasCode<Integer> {
    UNKNOWN(0, "未知"),
    A(1, "场景A"),
    B(2, "场景B"),
    ;

    @EnumValue
    private final Integer code;
    @Getter
    private final String  name;

    @Override
    public Integer getCode() {
        return code;
    }
}
