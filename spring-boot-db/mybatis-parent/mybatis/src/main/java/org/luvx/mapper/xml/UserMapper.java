package org.luvx.mapper.xml;

import org.apache.ibatis.annotations.Param;
import org.luvx.common.entity.User;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface UserMapper {
    User selectByPrimaryKey(@Param("id") Serializable id);
}
