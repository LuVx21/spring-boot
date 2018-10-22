package org.luvx.pattern.structural.Decorator;

/**
 * 装饰器,和具体被装饰者实现共同接口
 */

public abstract class PrinterDecorater implements Printer {
    private Printer printer;

    public PrinterDecorater(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void printlog() {
        printer.printlog();
    }
}
