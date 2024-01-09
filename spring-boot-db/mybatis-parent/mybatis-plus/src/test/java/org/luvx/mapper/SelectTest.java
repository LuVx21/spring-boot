package org.luvx.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.coding.common.more.MorePrints;
import org.luvx.entity.User;
import org.luvx.enums.BizTypeEnum;
import org.luvx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.luvx.entity.User.*;

@Slf4j
class SelectTest extends ApplicationTests {
    @Autowired
    private UserMapper  userMapper;
    @Autowired
    private UserService userService;

    @Test
    void selectTest() {
        QueryWrapper<User> query = Wrappers.<User>query()
                .select("distinct " + User.COL_BIZ_TYPE)
                .isNotNull(COL_ID);
        List<BizTypeEnum> collect = userMapper.selectList(query).stream()
                .map(User::getBizType)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    void selectByIdTest() {
        User user = userMapper.selectById(1);

        List<Long> ids = List.of(0L, 4L, 5L);
        List<User> users = userMapper.selectBatchIds(ids);
        MorePrints.println(user, users);
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
        QueryWrapper<User> query = Wrappers.<User>query()
                .select(COL_ID, COL_AGE)
                .gt(COL_ID, 10000L)
                .orderByAsc(COL_ID)
                .last("limit 1")
                // .in(COL_ID, List.of(10000L, 10001L))
                // .eq(COL_ID, 10000L)
                // .and(w -> w.eq(COL_ID, 1L).ne(COL_ID, 2L))
                // .eq(COL_ID, 3L)
                ;
        // LambdaQueryWrapper<User> query = Wrappers.<User>lambdaQuery()
        //         .eq(User::getUserId, 10000L);

        // User user = userService.getOne(query);
        User user = userMapper.selectOne(query);
        System.out.println(user);
    }

    @Test
    void selectCountTest() {
        long count = userMapper.selectCount(new QueryWrapper<User>().eq(COL_AGE, "3"));
        System.out.println(count);
    }

    @Test
    void selectListTest() {
        QueryWrapper<User> query = new QueryWrapper<User>()
                .eq(COL_AGE, "3")
                // .like(COL_UNAME, "foo")
                // .likeLeft(COL_UNAME, "foo")
                .likeRight(COL_UNAME, "foo");
        List<User> users = userMapper.selectList(query);
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

        MorePrints.println(page.getRecords(), iPage.getRecords());
    }

    @Test
    void selectMapsPageTest() {
        Page<Map<String, Object>> page = new Page<>(0, 3);
        IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, new QueryWrapper<User>()
                .ge(COL_AGE, "3")
        );
        MorePrints.println(page.getRecords(), iPage.getRecords());
    }
}