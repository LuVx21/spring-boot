package org.luvx.mapper;

import org.luvx.common.BaseMapper;
import org.luvx.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 测试批量操作(针对主键)
 */
@Repository
public interface UserMapper3 extends BaseMapper<User> {
}