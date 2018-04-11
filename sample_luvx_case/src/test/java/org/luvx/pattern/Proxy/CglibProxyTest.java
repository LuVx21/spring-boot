package org.luvx.pattern.Proxy;

import org.junit.Test;

public class CglibProxyTest {

    /**
     * Cglib动态代理
     */
    @Test
    public void run01() {
        LogPrinter logPrinter = (LogPrinter) new CglibProxy().newProxyInstance(new LogPrinter());
        logPrinter.printlog();
        logPrinter.pringlogNo();
    }
}
