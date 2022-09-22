package org.luvx.tools.service.commonkv;

import static java.util.Collections.singleton;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.luvx.common.util.JsonUtils;
import org.luvx.tools.dao.entity.CommonKeyValue;
import org.luvx.tools.service.commonkv.constant.KVBizType;

public interface CommonKeyValueService {

    /**
     * 存在会覆盖
     */
    <T> boolean setValue(KVBizType bizType, String key, T value);

    /**
     * 存在不覆盖, 返回 false
     */
    <T> boolean setValueIfAbsent(KVBizType bizType, String key, T value);

    /**
     * 数值类型增加计数, 仅支持 Integer 和 Long
     */
    boolean setOrIncrValue(KVBizType bizType, String key, long incr);

    void remove(KVBizType bizType, String key);

    void setValueItem(KVBizType bizType, String key, Map<String, Object> kvs);

    void setValueItemIfAbsent(KVBizType bizType, String key, Map<String, Object> kvs);

    /**
     * 仅支持 json object的最外层 key 的删除
     */
    void removeValueItem(KVBizType bizType, String key, String... itemKey);

    Map<String, CommonKeyValue> get(KVBizType bizType, Collection<String> keys);

    default Optional<CommonKeyValue> get(KVBizType bizType, String key) {
        return Optional.ofNullable(get(bizType, singleton(key)).get(key));
    }

    default <T> Map<String, T> getData(KVBizType bizType, Collection<String> keys) {
        return get(bizType, keys).entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey,
                        e -> (T) JsonUtils.fromJson(e.getValue().getCommonValue(), bizType.getValueClass())
                ));
    }

    default <T> Optional<T> getData(KVBizType bizType, String key) {
        return get(bizType, key)
                .map(kv -> (T) JsonUtils.fromJson(kv.getCommonValue(), bizType.getValueClass()));
    }
}
