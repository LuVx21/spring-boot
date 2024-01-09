package org.luvx.mapper.dynamic;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.common.entity.User;
import org.luvx.mapper.dynamic.DynamicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DynamicMapperTest {

    @Autowired
    DynamicMapper dynamicMapper;

    @Test
    public void insertTest() {
        User user = new User();
        user.setUserName("foobar");
        user.setPassword("1234");
        user.setAge(16);

        ImmutableMap<String, Object> iMap = ImmutableMap.of("tableName", "user", "user", user);
        dynamicMapper.insertByCon(iMap);
    }

    @Test
    public void insertListByConTest() {
        User user = new User();
        user.setUserName("foobar");
        user.setPassword("1234");
        user.setAge(16);

        ImmutableList<User> iList = ImmutableList.of(user, user);
        ImmutableMap<String, Object> map = ImmutableMap.of("tableName", "user", "user", iList);

        dynamicMapper.insertListByCon(map);
    }

    @Test
    public void deleteTest() {
        User user = new User();
        user.setUserName("foobar");

        // ImmutableMap<String, Object> iMap = ImmutableMap.of("tableName", "user", "user", user);
        // dynamicMapper.deleteByCon(iMap);

        dynamicMapper.deleteByCon1("user", user);
    }

    @Test
    public void updateTest() {
        User user = new User();
        user.setUserName("foobar");
        User newUser = new User();
        newUser.setUserName("renxie");

        ImmutableMap<String, Object> iMap = ImmutableMap.of("tableName", "user",
                "user", user,
                "newUser", newUser);
        dynamicMapper.updateByCon(iMap);
    }

    @Test
    public void selectByCon() {
        User user = new User();
        user.setId(1L);
        user.setUserName("foo");

        ImmutableMap<String, Object> iMap = ImmutableMap.of("tableName", "user", "user", user);
        List<User> list = dynamicMapper.selectByCon(iMap);
        System.out.println(list);
    }
}