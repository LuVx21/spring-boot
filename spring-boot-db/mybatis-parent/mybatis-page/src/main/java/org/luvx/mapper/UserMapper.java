package org.luvx.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.luvx.entity.User;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    Page<User> getUserList();

    User selectByPrimaryKey(Long id);

    Page<User> selectSelective(User record);
}
