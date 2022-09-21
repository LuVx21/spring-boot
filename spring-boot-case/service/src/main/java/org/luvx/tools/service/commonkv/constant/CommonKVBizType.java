package org.luvx.tools.service.commonkv.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.luvx.common.enums.EnumHasCode;

import java.util.Map;

@AllArgsConstructor
public enum CommonKVBizType implements KVBizType, EnumHasCode<Integer> {
    UNKNOWN(0, Object.class),
    INT(1, Integer.class),
    STRING(2, String.class),
    MAP(3, Map.class),
    ;

    private final int      code;
    @Getter
    private final Class<?> valueClass;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public Class<?> valueClass() {
        return getValueClass();
    }
}
