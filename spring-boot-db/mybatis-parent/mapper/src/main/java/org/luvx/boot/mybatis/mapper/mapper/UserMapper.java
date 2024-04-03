package org.luvx.boot.mybatis.mapper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.luvx.boot.mybatis.mapper.common.base.BaseMapper;
import org.luvx.boot.mybatis.mapper.entity.User;

/**
 * @ClassName: org.luvx.mapper
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 17:47
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
