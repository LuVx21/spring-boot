package org.luvx.boot.mars.grpc.sdk.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.luvx.boot.mars.grpc.sdk.CountType;

@Getter
@AllArgsConstructor
public enum UserCountType implements CountType {
    UNKNOWN(0, "未知"),
    FANS_COUNT(1, "粉丝量"),
    ;

    private final int    type;
    private final String name;
}
