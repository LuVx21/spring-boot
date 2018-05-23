package org.luvx.nospring.dynamic;

import org.junit.Test;
import org.luvx.service.Printable;
import org.luvx.service.impl.DocPrinter;
import org.luvx.service.impl.LogPrinter;

public class ProxyFatoryTest {
    /**
     * jdk实现
     */
    @Test
    public void run01() {
        Printable printer = new LogPrinter();
        Printable proxyPrinter = (Printable) ProxyFatory.getProxyFatory().newProxyInstance(printer);
        proxyPrinter.print();
    }

    @Test
    public void run02() {
        Printable printer = new LogPrinter();
        Printable proxyPrinter = (Printable) ProxyFatorySimple.newProxyInstance(printer);
        proxyPrinter.print();
    }

    /**
     * cglib实现
     */
    @Test
    public void run03() {
        DocPrinter printer = new DocPrinter();
        DocPrinter proxyPrinter = (DocPrinter) new ProxyFatoryCglib().newProxyInstance(printer);
        proxyPrinter.print();
    }
}
