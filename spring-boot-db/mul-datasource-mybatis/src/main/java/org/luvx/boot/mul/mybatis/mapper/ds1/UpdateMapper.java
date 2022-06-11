package org.luvx.boot.mul.mybatis.mapper.ds1;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.luvx.boot.mul.mybatis.config.DS;
import org.luvx.boot.mul.mybatis.entity.User;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@DS(value = DS.DSType.ds1)
@Mapper
@Repository
public interface UpdateMapper {
    User selectByPrimaryKey(@Param("id") Serializable id);

    /**
     * 用于确认事务
     */
    int updateByPrimaryKey(@Param("record") User record);
}
