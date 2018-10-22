package org.luvx.pattern.structural.Decorator;

/**
 * 另一个装饰器
 * 用于配合其他装饰器多次装饰同一对象
 */
public class LogPrinterDecorater1 extends PrinterDecorater {
    public LogPrinterDecorater1(Printer printer) {
        super(printer);
    }

    public void beforePrint1() {
        System.out.println("打印前另一个操作");
    }

    public void afterPrint1() {
        System.out.println("打印后另一个操作");
    }

    @Override
    public void printlog() {
        this.beforePrint1();
        super.printlog();
        this.afterPrint1();
    }
}
