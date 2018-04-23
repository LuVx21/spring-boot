package org.luvx.pattern.Proxy.dynamic;

import org.junit.Test;
import org.luvx.pattern.Proxy.LogPrinter;
import org.luvx.pattern.Proxy.Printable;

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
