package org.luvx.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.vavr.API;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    void selectByIdTest() {
        User user = userMapper.selectById(1);

        List<Long> ids = List.of(0L, 4L, 5L);
        List<User> users = userMapper.selectBatchIds(ids);
        API.println(user, users);
    }

    @Test
    void selectByMapTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_name", "Luvx");
        map.put("password", "0000");
        map.put("age", 3);

        List<User> users = userMapper.selectByMap(map);
        System.out.println(users);
    }

    @Test
    void selectOneTest() {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("user_id", 0L));
        System.out.println(user);
    }

    @Test
    void selectCountTest() {
        long count = userMapper.selectCount(new QueryWrapper<User>().eq("age", "3"));
        System.out.println(count);
    }

    @Test
    void selectListTest() {
        List<User> users = userMapper.selectList(new QueryWrapper<User>()
                .eq("age", "3"));
        System.out.println(users);
    }

    @Test
    void selectMapsTest() {
        List<Map<String, Object>> users = userMapper.selectMaps(new QueryWrapper<User>()
                .eq("age", "3")
                .ne("password", "0000"));
        System.out.println(users);
    }

    @Test
    void selectObjsTest() {
        List<Object> objs = userMapper.selectObjs(new QueryWrapper<User>()
                .eq("age", "3"));
        System.out.println(objs);
    }

    @Test
    void selectPageTest() {
        Page<User> page = new Page<>(0, 3);
        IPage<User> iPage = userMapper.selectPage(page, new QueryWrapper<User>()
                .eq("age", "3"));

        API.println(page.getRecords(), iPage.getRecords());
    }

    @Test
    void selectMapsPageTest() {
        Page<Map<String, Object>> page = new Page<>(0, 3);
        IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, new QueryWrapper<User>()
                .ge("age", "3")
        );
        API.println(page.getRecords(), iPage.getRecords());
    }
}