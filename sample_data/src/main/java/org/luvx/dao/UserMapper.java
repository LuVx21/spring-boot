package org.luvx.dao;

import org.luvx.entity.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper {

    void save(User user);

    User findById(long id);
    // 无效更新
    void update(User user);

    void deleteById(long id);
}
