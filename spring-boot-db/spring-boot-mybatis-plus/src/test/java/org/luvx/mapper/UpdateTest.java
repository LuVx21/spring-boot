package org.luvx.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class UpdateTest extends ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * <pre>
     *     UPDATE user SET user_name=? WHERE id=?
     * </pre>
     */
    @Test
    void updateByIdTest() {
        int num = userMapper.updateById(User.builder().userId(10000L).userName("LuVx1").build());
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