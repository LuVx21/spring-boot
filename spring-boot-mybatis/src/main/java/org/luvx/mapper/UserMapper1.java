package org.luvx.mapper;

import org.luvx.common.BaseMapper;
import org.luvx.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 测试单数据操作(针对主键)
 */
@Repository
public interface UserMapper1 extends BaseMapper<User> {
}