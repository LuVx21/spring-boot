package org.luvx.pattern.Proxy;

import org.junit.Test;

public class LogPrinterHandlerTest {

    /**
     * jdk动态代理
     */
    @Test
    public void run01() {
        Printer logPrinter = (Printer) new LogPrinterHandler().newProxyInstance(new LogPrinter());
        logPrinter.printlog();
    }
}
