package org.luvx.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import static org.luvx.entity.User.*;

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
        int num = userMapper.updateById(User.builder().id(10000L).userName("LuVx1").build());
        log.info("{}", num);
    }

    /**
     * <pre>
     *     UPDATE user SET user_name=? WHERE (id = ?)
     * </pre>
     */
    @Test
    void updateTest() {
        // User user2 = User.builder().userName("LuVx2").build();
        // QueryWrapper<User> query = Wrappers.<User>query()
        //         .eq(COL_ID, 10000L);
        // int num = userMapper.update(user2, query);

        Wrapper<User> query1 = Wrappers.<User>update()
                .set(COL_UNAME, "LuVx3")
                .set(COL_AGE, null)
                .eq(COL_ID, 10000L);
        userMapper.update(null, query1);
    }
}