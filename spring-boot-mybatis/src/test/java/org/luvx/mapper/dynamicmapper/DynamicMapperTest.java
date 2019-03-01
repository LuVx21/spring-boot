package org.luvx.mapper.dynamicmapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @ClassName: org.luvx.mapper.dynamicmapper
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/2/21 17:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicMapperTest {

    @Autowired
    DynamicMapper dynamicMapper;

    @Test
    public void selectByCon() {
        User user = new User();
        user.setUserId(3L);
        user.setUserName("default");
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", "user");
        map.put("user", user);
        dynamicMapper.selectByCon(map);
    }
}