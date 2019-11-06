package org.luvx.service;


import org.luvx.pojos.User;

import java.util.Iterator;

/**
 * @ClassName: org.luvx.service
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/11/6 9:58
 */
public interface UserService {
    void delete(int id);

    void insert(User user);

    int update(User user);

    User selectById(int id);

    Iterator<User> selectAll(int pageNum, int pageSize);
}
