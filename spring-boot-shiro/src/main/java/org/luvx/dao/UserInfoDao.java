package org.luvx.dao;

import org.luvx.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoDao extends CrudRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
}