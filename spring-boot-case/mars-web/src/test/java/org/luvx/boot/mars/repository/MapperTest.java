package org.luvx.boot.mars.repository;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.luvx.boot.mars.BaseAppTests;
import org.luvx.boot.mars.dao.entity.Count;
import org.luvx.boot.mars.dao.mapper.CountMapper;
import org.luvx.boot.mars.dao.mapper.OssFileMapper;

class MapperTest extends BaseAppTests {
    @Resource
    private OssFileMapper ossFileMapper;
    @Resource
    private CountMapper   countMapper;

    // @Resource
    // private RedisTemplate<String, Object> redisTemplate;
    @Test
    void m1() throws Exception {
        Count count = countMapper.selectByPrimaryKey(10000L).get();
        System.out.println(count);
    }
}
