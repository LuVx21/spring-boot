package org.luvx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import org.luvx.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    @Insert("insert into user(user_name,password,age) values(#{userName}, #{passWord}, #{age})")
    void add(User user);

    @Delete("delete from user where id = #{id}")
    void deleteById(Long id);

    @Delete("delete from user where user_name = #{userName}")
    void deleteByUserName(String userName);

    @Update("update user set user_name = #{userName}, password = #{passWord}, age = #{age} where id = #{id}")
    void update(User user);

    @Select("select * from user")
    @Results({
            @Result(property = "userName", column = "user_name")
    })
    List<User> getAll();

    @Select("select * from user where id = #{id}")
    @Results({
            @Result(property = "userName", column = "user_name")
    })
    User getById(Long id);

}