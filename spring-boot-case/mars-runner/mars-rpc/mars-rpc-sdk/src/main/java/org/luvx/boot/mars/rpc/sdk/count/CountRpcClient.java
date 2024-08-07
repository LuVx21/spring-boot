package org.luvx.boot.mars.rpc.sdk.count;

import org.luvx.boot.mars.rpc.common.count.CountOperateType;
import org.luvx.boot.mars.rpc.common.count.CountType;

import java.util.Collection;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singleton;

public interface CountRpcClient {
    /**
     * 批量id的所有类型
     */
    Map<Long, Map<Integer, Integer>> getByIds(Collection<Long> ids);

    /**
     * 指定id的所有类型
     */
    default Map<Integer, Integer> getById(long countId) {
        return getByIds(singleton(countId)).getOrDefault(countId, emptyMap());
    }

    /**
     * 批量id的指定类型
     */
    Map<Long, Integer> getByType(CountType type, Collection<Long> countIds);

    /**
     * 指定id的指定类型
     */
    default long getByType(CountType type, long countId) {
        return getByType(type, singleton(countId)).getOrDefault(countId, 0);
    }

    /**
     * 增、减、赋值操作
     * 必须统一用operate 或 asyncOperate, 保证顺序性
     */
    void operate(CountOperateType event, long countId, Collection<CountType> types, int value);

    default void operate(CountOperateType event, long countId, CountType type, int value) {
        operate(event, countId, singleton(type), value);
    }

    /**
     * 增、减、赋值操作必须统一用operate 或 asyncOperate, 保证顺序性
     */
    void asyncOperate(CountOperateType event, long countId, Collection<CountType> types, int value);

    void batchSet(long countId, Map<CountType, Integer> countMap);
}
