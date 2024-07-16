package org.luvx.boot.mars.dao.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.luvx.coding.common.enums.EnumHasCode;

@Getter
@AllArgsConstructor
public enum RedisKeyPrefix implements EnumHasCode<String> {
    COUNT("c:", "计数服务"),
    ;

    private final String prefix;
    private final String name;

    @Override
    public String getCode() {
        return prefix;
    }

    public String redisKey(Object key) {
        return prefix + key;
    }
}
