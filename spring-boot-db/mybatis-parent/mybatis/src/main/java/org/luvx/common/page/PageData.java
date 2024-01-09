package org.luvx.common.page;

import lombok.Data;

import java.util.List;

@Data
public class PageData<T> extends Page {
    private List<T> modelLists;
}
