package org.luvx.common.base;

import java.util.Collection;

/**
 * @ClassName: org.luvx.common.base
 * @Description: BaseMapper 接口
 * 1.以主键查询
 * 2.以对象查询
 * 3.批量查询
 * @Author: Ren, Xie
 * @Date: 2019/3/11 14:50
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {
    /**
     * 批量插入
     *
     * @param records
     * @return
     */
    int insertBatch(Collection<T> records);
}