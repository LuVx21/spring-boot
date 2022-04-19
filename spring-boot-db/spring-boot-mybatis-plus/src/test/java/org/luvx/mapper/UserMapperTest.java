package org.luvx.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class UserMapperTest extends ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void insertTest() {
        User user = User.builder()
                // .userId(1L)
                .userName("xie")
                .password("ren")
                .age(18)
                .build();

        userMapper.insert(user);
        System.out.println(user.getUserId());
    }

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

    /**
     * <pre>
     *     UPDATE user SET user_name=? WHERE id=?
     * </pre>
     */
    @Test
    void updateByIdTest() {
        int num = userMapper.updateById(User.builder().userId(10043L).userName("LuVx1").build());
        log.info(num + "");
    }

    /**
     * <pre>
     *     UPDATE user SET user_name=? WHERE (id = ?)
     * </pre>
     */
    @Test
    void updateTest() {
        int num = userMapper.update(User.builder().userId(9999L).userName("LuVx2").build(),
                new QueryWrapper<User>().eq("id", 10043L));
        System.out.println(num);
    }
}