package org.luvx.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.luvx.common.base.BaseMapper;
import org.luvx.entity.User;

/**
 * @ClassName: org.luvx.mapper
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 17:47
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
