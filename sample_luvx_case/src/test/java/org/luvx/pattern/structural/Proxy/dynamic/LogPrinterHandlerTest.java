package org.luvx.pattern.structural.Proxy.dynamic;

import org.junit.Test;
import org.luvx.pattern.structural.Proxy.LogPrinter;
import org.luvx.pattern.structural.Proxy.Printable;

public class LogPrinterHandlerTest {

    /**
     * jdk动态代理
     */
    @Test
    public void run01() {
        Printable logPrinter = (Printable) new LogPrinterHandler().newProxyInstance(new LogPrinter());
        logPrinter.printlog();
    }
}
