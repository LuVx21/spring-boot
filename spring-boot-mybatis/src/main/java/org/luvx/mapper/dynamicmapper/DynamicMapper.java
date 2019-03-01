package org.luvx.mapper.dynamicmapper;

/**
 * @ClassName: org.luvx.mapper.dynamicmapper
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/2/21 17:28
 */

import org.luvx.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DynamicMapper {

    // @Param("tableName") String tableName, User user


    List<User> selectByCon(Map<String, Object> argsMap);

}
