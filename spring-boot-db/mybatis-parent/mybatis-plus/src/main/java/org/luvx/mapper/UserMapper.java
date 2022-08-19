package org.luvx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.luvx.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}