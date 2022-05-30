package org.luvx.boot.mul.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.luvx.boot.mul.mybatis.config.DS;
import org.luvx.boot.mul.mybatis.entity.User;
import org.springframework.stereotype.Repository;

@DS(value = DS.DSType.write)
@Mapper
@Repository
public interface UpdateMapper {
    int updateByPrimaryKey(@Param("record") User record);
}
