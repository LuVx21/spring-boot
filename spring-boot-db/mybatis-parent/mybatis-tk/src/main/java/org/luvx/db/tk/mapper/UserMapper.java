package org.luvx.db.tk.mapper;

import io.mybatis.mapper.Mapper;
import org.luvx.db.tk.entity.User;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User, Long> {
}
