package org.luvx.pattern.Proxy.dynamic;

import org.junit.Test;
import org.luvx.pattern.Proxy.NewLogPrinter;

public class CglibProxyTest {

    /**
     * Cglib动态代理
     */
    @Test
    public void run01() {
        NewLogPrinter logPrinter = (NewLogPrinter) new CglibProxy().newProxyInstance(new NewLogPrinter());
        logPrinter.printlog();
        logPrinter.pringlogNo();
    }
}
