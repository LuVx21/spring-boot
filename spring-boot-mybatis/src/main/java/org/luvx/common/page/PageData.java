package org.luvx.common.page;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: org.luvx.common.page
 * @Description: 分页返回用
 * @Author: Ren, Xie
 * @Date: 2019/3/6 11:35
 */
@Data
public class PageData<T> extends Page {
    private List<T> modelLists;
}
