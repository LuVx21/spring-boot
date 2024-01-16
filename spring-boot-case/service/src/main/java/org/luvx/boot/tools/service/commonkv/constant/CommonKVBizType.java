package org.luvx.boot.tools.service.commonkv.constant;

import lombok.AllArgsConstructor;
import org.luvx.boot.tools.dao.entity.User;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public enum CommonKVBizType implements KVBizType {
    UNKNOWN(0, Object.class),
    LONG(1, Long.class),
    STRING(2, String.class),
    MAP(3, Map.class),
    BEAN(4, User.class),
    LIST(5, List.class),
    ARRAY(6, String[].class),
    ;

    private final int      bizType;
    private final Class<?> valueClass;

    @Override
    public Integer getCode() {
        return bizType;
    }

    @Override
    public boolean isValidBizCode() {
        return this != UNKNOWN;
    }

    @Override
    public Class<?> getValueClass() {
        return valueClass;
    }
}
