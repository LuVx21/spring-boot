package org.luvx.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
class DeleteTest extends ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void deleteByIdTest() {
        userMapper.deleteById(10015L);

        User u = User.builder().userId(10015L).userName("foo1").build();
        userMapper.deleteById(u);
    }

    @Test
    void deleteByMapTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("password", "default5");

        userMapper.deleteByMap(map);
    }

    @Test
    void deleteTest() {
        int num = userMapper.delete(new QueryWrapper<User>().eq("password", "default10000"));
        System.out.println(num);
    }

    @Test
    void deleteBatchIdsTest() {
        List<Long> ids = List.of(10012L, 10014L);
        int num = userMapper.deleteBatchIds(ids);
        System.out.println(num);
    }
}