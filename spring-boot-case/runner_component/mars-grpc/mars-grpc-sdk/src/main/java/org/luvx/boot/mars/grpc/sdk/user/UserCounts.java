package org.luvx.boot.mars.grpc.sdk.user;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMaps;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.MapUtils;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@ToString
@RequiredArgsConstructor
public class UserCounts {
    private final Int2IntMap userCounts;

    public Int2IntMap valueMap() {
        return defaultIfNull(userCounts, Int2IntMaps.EMPTY_MAP);
    }

    public int getByType(UserCountType countType) {
        if (MapUtils.isEmpty(userCounts)) {
            return 0;
        }
        return userCounts.getOrDefault(countType.getType(), 0);
    }
}
