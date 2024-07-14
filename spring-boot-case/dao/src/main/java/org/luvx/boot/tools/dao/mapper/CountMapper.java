package org.luvx.boot.tools.dao.mapper;

import io.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.luvx.boot.tools.dao.entity.Count;

@org.apache.ibatis.annotations.Mapper
public interface CountMapper extends Mapper<Count, Long> {

    @Update("""
            update count set count_value = count_value + #{delta}
            where count_id = #{countId} and count_type = #{type}
            """)
    void incr(@Param("countId") long countId, @Param("type") int type, @Param("delta") int delta);
}