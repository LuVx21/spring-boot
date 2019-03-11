package org.luvx.common.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @ClassName: org.luvx.common.base
 * @Description: 基础查询实体
 * @Author: Ren, Xie
 * @Date: 2019/3/11 14:50
 */
@Data
public class BaseQueryEntity {
    /**
     * 去重复
     */
    @TableField(exist = false, select = false)
    protected Boolean distinctCon;

    /**
     * 要查询的列
     */
    @TableField(exist = false, select = false)
    protected String selectColumns;

    /**
     * 查询条件
     */
    @TableField(exist = false, select = false)
    protected String whereCon;

    /**
     * 排序
     */
    @TableField(exist = false, select = false)
    protected String orderCon;

    /**
     * 分组
     */
    @TableField(exist = false, select = false)
    protected String groupCon;

    /**
     * having
     */
    @TableField(exist = false, select = false)
    protected String havingCon;

    /**
     * limit
     */
    @TableField(exist = false, select = false)
    protected String limitCon;
}