package org.luvx.db.tk.mapper;

import io.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Select;
import org.luvx.db.tk.entity.User;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User, Long> {
    @Select("${_sql}")
    List<User> selectCustom(String _sql);
}
