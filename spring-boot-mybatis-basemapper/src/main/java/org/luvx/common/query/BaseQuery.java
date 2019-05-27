package org.luvx.common.query;

import lombok.Getter;

/**
 * @ClassName: org.luvx.query
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 17:02
 */
@Getter
public abstract class BaseQuery<T> implements Query<T> {
    private Integer limit  = 1;
    private Integer page   = 0;
    private Integer offset = 0;
    private String  sort;
    private String  order;

    public void setLimit(Integer limit) {
        this.limit = limit > 0 ? limit : 1;
        this.offset = this.page * this.limit;
    }

    public void setPage(Integer page) {
        if (page == null) {
            page = 0;
        }
        this.page = page >= 0 ? page : 0;
        this.offset = this.page * this.limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.toString();
    }

    public void setOrder(String order) {
        this.order = order == null ? null : order.toString();
    }
}
