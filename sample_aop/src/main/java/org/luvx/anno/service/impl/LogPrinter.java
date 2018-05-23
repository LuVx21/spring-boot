package org.luvx.anno.service.impl;

import org.luvx.anno.service.Printable;

/**
 * 例:日志打印
 * 实现接口,JDK动态代理实现用
 */
public class LogPrinter implements Printable {

    @Override
    public void print() {
        System.out.println("jdk:打印日志......");
    }
}
