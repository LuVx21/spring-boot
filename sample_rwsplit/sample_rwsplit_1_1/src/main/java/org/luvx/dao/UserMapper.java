package org.luvx.dao;

import org.luvx.core.DataSource;
import org.luvx.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    @DataSource("write")
    void save(User user);

    @DataSource("read")
    User findById(long id);

    void update(User user);

    void deleteById(long id);
}
