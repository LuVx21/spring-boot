package org.luvx.pattern.structural.Decorator;

/**
 * 具体装饰器
 * 用于装饰具体被装饰者
 */
public class LogPrinterDecorater extends PrinterDecorater {
    public LogPrinterDecorater(Printer printer) {
        super(printer);
    }

    public void beforePrint() {
        System.out.println("打印前操作...");
    }

    public void afterPrint() {
        System.out.println("打印后操作...");
    }

    @Override
    public void printlog() {
        this.beforePrint();
        super.printlog();
        this.afterPrint();
    }
}
