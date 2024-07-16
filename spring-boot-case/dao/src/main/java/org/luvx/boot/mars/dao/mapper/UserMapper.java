package org.luvx.boot.mars.dao.mapper;

import org.luvx.boot.mars.dao.entity.User;

import io.mybatis.mapper.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User, Long> {
}