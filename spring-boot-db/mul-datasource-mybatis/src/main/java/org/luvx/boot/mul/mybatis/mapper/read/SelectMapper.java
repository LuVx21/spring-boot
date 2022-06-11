package org.luvx.boot.mul.mybatis.mapper.read;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.luvx.boot.mul.mybatis.config.DS;
import org.luvx.boot.mul.mybatis.entity.User;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@DS(value = DS.DSType.read)
@Mapper
@Repository
public interface SelectMapper {
    User selectByPrimaryKey(@Param("id") Serializable id);

    /**
     * 用于确认事务
     */
    int updateByPrimaryKey(@Param("record") User record);
}
