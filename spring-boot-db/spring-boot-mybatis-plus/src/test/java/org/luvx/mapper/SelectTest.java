package org.luvx.mapper;

import static org.luvx.entity.User.COL_AGE;
import static org.luvx.entity.User.COL_ID;
import static org.luvx.entity.User.COL_PWD;
import static org.luvx.entity.User.COL_UNAME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.vavr.API;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class SelectTest extends ApplicationTests {

    @Autowired
    private UserMapper userMapper;

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
        map.put(COL_UNAME, "Luvx");
        map.put(COL_PWD, "0000");
        map.put(COL_AGE, 3);

        List<User> users = userMapper.selectByMap(map);
        System.out.println(users);
    }

    /**
     * <pre>
     *     select id as userid,user_name,password,age,update_time
     *     from user
     *     where id = ?
     *     and (id = ? and id <> ?)
     *     and id = ?
     * </pre>
     */
    @Test
    void selectOneTest() {
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .eq(COL_ID, 0L)
                .and(w -> w.eq(COL_ID, 1L).ne(COL_ID, 2L))
                .eq(COL_ID, 3L)
        );
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
                .eq(COL_AGE, "3"));
        System.out.println(users);
    }

    @Test
    void selectMapsTest() {
        List<Map<String, Object>> users = userMapper.selectMaps(new QueryWrapper<User>()
                .eq(COL_AGE, "3")
                .ne(COL_PWD, "0000"));
        System.out.println(users);
    }

    @Test
    void selectObjsTest() {
        List<Object> objs = userMapper.selectObjs(new QueryWrapper<User>()
                .eq(COL_AGE, "3"));
        System.out.println(objs);
    }

    @Test
    void selectPageTest() {
        Page<User> page = new Page<>(0, 3);
        IPage<User> iPage = userMapper.selectPage(page, new QueryWrapper<User>()
                .eq(COL_AGE, "3"));

        API.println(page.getRecords(), iPage.getRecords());
    }

    @Test
    void selectMapsPageTest() {
        Page<Map<String, Object>> page = new Page<>(0, 3);
        IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, new QueryWrapper<User>()
                .ge(COL_AGE, "3")
        );
        API.println(page.getRecords(), iPage.getRecords());
    }
}