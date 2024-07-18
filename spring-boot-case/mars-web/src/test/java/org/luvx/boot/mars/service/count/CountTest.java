package org.luvx.boot.mars.service.count;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.mars.BaseAppTests;
import org.luvx.boot.mars.dao.mapper.CountMapper;
import org.luvx.boot.mars.rpc.sdk.user.UserCountType;
import org.luvx.boot.mars.service.count.impl.CountRedisHelper;
import org.luvx.boot.mars.service.count.impl.CountService;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
class CountTest extends BaseAppTests {
    @Resource
    private CountRedisHelper countRedisHelper;
    @Resource
    private CountService     countService;
    @Resource
    private CountMapper      countMapper;

    @Test
    void m1() {
        countRedisHelper.setValue(10000L, List.of(UserCountType.FANS_COUNT, UserCountType.STAR_COUNT), 100);
        countRedisHelper.setValue(10001L, List.of(UserCountType.FANS_COUNT, UserCountType.STAR_COUNT), 200);
        Map<Long, Map<Integer, Integer>> m = countRedisHelper.getByCountIds(List.of(10000L, 10001L));
        System.out.println(m);
        Map<Long, Integer> m1 = countRedisHelper.getByType(List.of(10000L, 10001L), UserCountType.FANS_COUNT);
        System.out.println(m1);
    }

    @Test
    void m2() {
        countMapper.incr(10000L, 1, 35);
    }

    @Test
    void m3() {
        List<Long> ids = List.of(1L, 10000L, 10002L);
        Map<Long, Map<Integer, Integer>> m = countService.getByIds(ids);
        System.out.println(m);
        Map<Long, Integer> m1 = countService.getByType(UserCountType.STAR_COUNT, ids);
        System.out.println(m1);
    }
}