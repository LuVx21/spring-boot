package org.luvx.tools.web.base.spring.listener.enums;

import org.luvx.common.enums.EnumHasCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EventType implements EnumHasCode<Integer> {
    CREATE(1, "创建"),
    UPDATE(2, "更新"),
    DELETE(3, "删除"),
    ;

    private final Integer code;
    @Getter
    private final String  name;

    @Override
    public Integer getCode() {
        return code;
    }
}
