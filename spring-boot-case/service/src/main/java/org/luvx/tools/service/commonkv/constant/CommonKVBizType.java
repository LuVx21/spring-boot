package org.luvx.tools.service.commonkv.constant;

import java.util.Map;

import org.luvx.tools.dao.entity.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CommonKVBizType implements KVBizType {
    UNKNOWN(0, Object.class),
    INT(1, Integer.class),
    STRING(2, String.class),
    MAP(3, Map.class),
    BEAN(4, User.class),
    ;

    private final int      bizType;
    private final Class<?> valueClass;

    @Override
    public Integer getCode() {
        return bizType;
    }

    @Override
    public Class<?> getValueClass() {
        return valueClass;
    }
}
