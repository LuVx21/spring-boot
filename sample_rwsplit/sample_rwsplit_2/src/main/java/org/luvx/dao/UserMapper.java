package org.luvx.dao;

import org.luvx.core.DataSource;
import org.luvx.core.RorW;
import org.luvx.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    @DataSource(RorW.WRITE)
    void save(User user);

    @DataSource(RorW.READ)
    User findById(long id);

    void update(User user);

    void deleteById(long id);
}
