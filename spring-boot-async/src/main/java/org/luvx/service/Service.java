package org.luvx.service;

import java.util.concurrent.Future;

/**
 * @ClassName: org.luvx.service
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/2 16:10
 */
public interface Service {
    String method0();

    String method1();

    Future<String> method2();
}
