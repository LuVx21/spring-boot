package org.luvx.tools.dao.user.repository;

import io.mybatis.mapper.Mapper;
import org.luvx.tools.dao.user.entity.User;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User, Long> {
}