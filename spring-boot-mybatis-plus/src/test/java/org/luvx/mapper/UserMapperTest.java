package org.luvx.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
public class UserMapperTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertTest() {
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
    public void deleteByIdTest() {
        userMapper.deleteById(10015L);
    }

    @Test
    public void deleteByMapTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("password", "default5");

        userMapper.deleteByMap(map);
    }

    @Test
    public void deleteTest() {
        int num = userMapper.delete(new QueryWrapper<User>().eq("password", "default10000"));
        System.out.println(num);
    }

    @Test
    public void deleteBatchIdsTest() {
        List ids = Arrays.asList(10012, 10014);
        int num = userMapper.deleteBatchIds(ids);
        System.out.println(num);
    }

    @Test
    public void updateByIdTest() {
        int num = userMapper.updateById(User.builder().userId(9999L).userName("Luvx1").build());
        log.info(num + "");
    }

    @Test
    public void updateTest() {
        int num = userMapper.update(User.builder().userId(9999L).userName("Luvx2").build(),
                new QueryWrapper<User>().eq("user_id", 9999L));
        System.out.println(num);
    }

    @Test
    public void selectByIdTest() {
        User user = userMapper.selectById(0);
        System.out.println(user);
    }

    @Test
    public void selectBatchIdsTest() {
        List ids = Arrays.asList(0, 4, 5);
        List<User> users = userMapper.selectBatchIds(ids);
        System.out.println(users);
    }

    @Test
    public void selectByMapTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_name", "Luvx");
        map.put("password", "0000");
        map.put("age", 3);

        List<User> users = userMapper.selectByMap(map);
        System.out.println(users);
    }

    @Test
    public void selectOneTest() {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("user_id", 0L));
        System.out.println(user);
    }

    @Test
    public void selectCountTest() {
        int count = userMapper.selectCount(new QueryWrapper<User>().eq("age", "3"));
        System.out.println(count);
    }

    @Test
    public void selectListTest() {
        List<User> users = userMapper.selectList(new QueryWrapper<User>()
                .eq("age", "3"));
        System.out.println(users);
    }

    @Test
    public void selectMapsTest() {
        List<Map<String, Object>> users = userMapper.selectMaps(new QueryWrapper<User>()
                .eq("age", "3")
                .ne("password", "0000"));

        System.out.println(users);
    }

    @Test
    public void selectObjsTest() {
        List<Object> objs = userMapper.selectObjs(new QueryWrapper<User>()
                .eq("age", "3"));
        System.out.println(objs);
    }

    @Test
    public void selectPageTest() {
        Page<User> page = new Page<>(0, 3);
        IPage<User> ipage = userMapper.selectPage(page, new QueryWrapper<User>()
                .eq("age", "3"));

        System.out.println(page.getRecords());
        System.out.println(ipage.getRecords());
    }

    @Test
    public void selectMapsPageTest() {
        Page<User> page = new Page<>(0, 3);
        IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, new QueryWrapper<User>()
                .eq("age", "3"));

        System.out.println(page.getRecords());
        System.out.println(iPage.getRecords());
    }
}