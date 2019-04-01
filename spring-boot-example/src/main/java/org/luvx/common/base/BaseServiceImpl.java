package org.luvx.common.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @ClassName: org.luvx.common.base
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/1 14:13
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T>
        extends ServiceImpl<M, T>
        implements BaseService<T> {
}
