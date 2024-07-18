package org.luvx.boot.mars.rpc.service.count;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.luvx.boot.mars.rpc.common.count.CountOperateType;
import org.luvx.boot.mars.rpc.common.count.CountType;
import org.luvx.boot.mars.rpc.sdk.count.CountRpcClient;
import org.luvx.boot.mars.service.count.impl.CountService;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.Map;

@Slf4j
@DubboService(version = "1.0.0")
public class CountRpcServiceImpl implements CountRpcClient {
    @Resource
    private CountService countService;

    @Override
    public Map<Long, Map<Integer, Integer>> getByIds(Collection<Long> ids) {
        return countService.getByIds(ids);
    }

    @Override
    public Map<Long, Integer> getByType(CountType type, Collection<Long> countIds) {
        return countService.getByType(type, countIds);
    }

    @Override
    public void operate(CountOperateType event, long countId, Collection<CountType> types, int value) {
        countService.operate(event, countId, types, value);
    }

    @Override
    public void asyncOperate(CountOperateType event, long countId, Collection<CountType> types, int value) {
        countService.asyncOperate(event, countId, types, value);
    }

    @Override
    public void batchSet(long countId, Map<CountType, Integer> countMap) {
        countService.batchSet(countId, countMap);
    }
}
