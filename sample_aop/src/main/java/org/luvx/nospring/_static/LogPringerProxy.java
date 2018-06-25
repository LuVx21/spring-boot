package org.luvx.nospring._static;

import org.luvx.nospring.aspect.PrinterAspect;
import org.luvx.service.Printable;

public class LogPringerProxy implements Printable {

    private static final PrinterAspect aspect = new PrinterAspect();

    @Override
    public void print() {
        aspect.before();
        System.out.println("打印日志......");
        aspect.after();
    }
}
