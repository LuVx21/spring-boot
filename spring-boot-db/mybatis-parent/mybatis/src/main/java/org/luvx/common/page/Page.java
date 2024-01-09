package org.luvx.common.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class Page implements Serializable {
    static final  long serialVersionUID   = 20161109L;
    private final int  DEF_PAGE_VIEW_SIZE = 10;

    @Setter
    private int currentPage = 1;
    @Setter
    private int pageSize    = 5;
    private int totalCount;
    private int totalPageCount;
    /**
     * 动作类型
     * <li>0：无动作</li>
     * <li>1：首页</li>
     * <li>2：前一页</li>
     * <li>3：后一页</li>
     * <li>4：末页</li>
     * <li>5：跳转页</li>
     * <li>6：重新设定每页记录数</li>
     */
    @Setter
    private int actionType;

    public Page() {
    }

    public Page(int currentPage, int pageSize) {
        this.currentPage = (currentPage <= 0) ? 1 : currentPage;
        this.pageSize = (pageSize <= 0) ? DEF_PAGE_VIEW_SIZE : pageSize;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = (totalCount < 0) ? 0 : totalCount;
        if (this.totalCount == 0) {
            this.currentPage = 0;
        }
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount % pageSize == 0
                ? totalPageCount / pageSize
                : (totalPageCount / pageSize) + 1;
    }

    // ###################################

    /**
     * 取得当前查询总页数
     *
     * @return
     */
    public int getAllPageNum() {
        return totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
    }

    /**
     * 当前页起始id
     *
     * @return
     */
    public int getStartNo() {
        return (currentPage - 1) * pageSize + 1;
    }

    /**
     * 当前页终止id
     *
     * @return
     */
    public int getEndNo() {
        return Math.min(currentPage * pageSize, totalCount);
    }

    /**
     * 取得前一显示页码
     *
     * @return
     */
    public int getPrePageNo() {
        return currentPage == 1 ? 1 : (currentPage - 1);
    }

    /**
     * 取得后一显示页码
     *
     * @return
     */
    public int getNextPageNo() {
        return Math.min(currentPage + 1, getAllPageNum());
    }
}
