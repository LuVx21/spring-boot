package org.luvx.boot.mars.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.boot.mars.TestApp;
import org.luvx.boot.tools.dao.entity.Count;
import org.luvx.boot.tools.dao.mapper.CountMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.annotation.Resource;

@Slf4j
@ActiveProfiles("db")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApp.class)
class MapperTest {
    @Resource
    private CountMapper countMapper;
    // @Resource
    // private RedisTemplate<String, Object> redisTemplate;

    @Test
    void m0() {
        Count count = countMapper.selectByPrimaryKey(10000L).get();
        System.out.println(count);
    }
}