package org.luvx.boot.tools.dao.mapper;

import org.luvx.boot.tools.dao.entity.User;

import io.mybatis.mapper.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User, Long> {
}