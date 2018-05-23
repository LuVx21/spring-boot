package org.luvx.nospring._static;

import org.junit.Test;

public class LogPringerProxyTest {

    @Test
    public void run01() {
        LogPringerProxy proxy = new LogPringerProxy();
        proxy.print();
    }
}
