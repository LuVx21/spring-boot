package org.luvx.boot.mars.service.oss;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.luvx.coding.common.enums.EnumHasCode;

@Getter
@AllArgsConstructor
public enum OssScopeEnum implements EnumHasCode<String> {
    WEIBO("weibo", "weibo"),
    ;

    private final String scope;
    private final String name;

    @Override
    public String getCode() {
        return scope;
    }
}
