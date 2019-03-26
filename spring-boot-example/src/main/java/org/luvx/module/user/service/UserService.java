package org.luvx.module.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.luvx.module.user.entity.User;
import org.luvx.module.user.pojo.model.UserModel;

/**
 * @ClassName: org.luvx.module.user.service
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/8 17:28
 */
public interface UserService {
    /**
     * 增
     *
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 删
     *
     * @param id
     * @return
     */
    int deleteUserById(Long id);

    /**
     * 改
     *
     * @param id
     * @param user
     * @return
     */
    int updateUser(Long id, User user);

    /**
     * 查
     *
     * @return
     */
    User getUserById(Long id);

    /**
     * 查
     *
     * @return
     */
    IPage<User> selectUsers(UserModel userModel);
}
