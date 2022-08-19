package org.luvx.mapper.xml;

import org.apache.ibatis.annotations.Param;
import org.luvx.common.entity.User;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @ClassName: org.luvx.mapper.complex
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/12 18:06
 */
@Repository
public interface UserMapper {
    User selectByPrimaryKey(@Param("id") Serializable id);
}
