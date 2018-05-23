package org.luvx.anno.service.impl;

import org.springframework.stereotype.Component;

/**
 * 例:文档打印
 * 不实现接口,cglib动态代理实现用
 */
@Component("docPrinter")
public class DocPrinter {

    public void print() {
        System.out.println("cglib:打印文档......");
    }

}
