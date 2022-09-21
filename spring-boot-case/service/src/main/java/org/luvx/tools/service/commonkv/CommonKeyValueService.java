package org.luvx.tools.service.commonkv;

import com.google.common.collect.Maps;
import org.luvx.common.util.JsonUtils;
import org.luvx.tools.dao.commonkv.CommonKeyValue;
import org.luvx.tools.service.commonkv.constant.KVBizType;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public interface CommonKeyValueService {

    <T> boolean setValue(KVBizType bizType, String key, T value);

    boolean setOrIncrValue(KVBizType bizType, String key, long incr);

    Map<String, CommonKeyValue> get(KVBizType bizType, Collection<String> keys);

    @Nullable
    default CommonKeyValue get(KVBizType bizType, String key) {
        return get(bizType, Collections.singleton(key)).get(key);
    }

    default <T> Map<String, T> getData(KVBizType bizType, Collection<String> keys) {
        return get(bizType, keys).entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey,
                        e -> (T) JsonUtils.fromJson(e.getValue().getValue(), bizType.valueClass())
                ));
    }

    @Nullable
    default <T> T getData(KVBizType bizType, String key) {
        CommonKeyValue kv = get(bizType, key);
        if (kv == null) {
            return null;
        }
        return (T) JsonUtils.fromJson(kv.getValue(), bizType.valueClass());
    }
}
