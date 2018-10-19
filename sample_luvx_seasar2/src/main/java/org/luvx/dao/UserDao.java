package org.luvx.dao;

import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;
import org.luvx.entity.User;

import java.util.List;

@S2Dao(bean = User.class)
public interface UserDao {

    @Sql("SELECT * FROM USER ")
    List<User> getUsers();
}
