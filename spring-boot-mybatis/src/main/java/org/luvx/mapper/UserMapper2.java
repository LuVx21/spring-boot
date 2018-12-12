package org.luvx.mapper;

import org.luvx.common.BaseMapper;
import org.luvx.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 测试单数据操作(针对对象)
 */
@Repository
public interface UserMapper2 extends BaseMapper<User> {
}