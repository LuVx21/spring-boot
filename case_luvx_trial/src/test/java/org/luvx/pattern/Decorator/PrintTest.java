package org.luvx.pattern.Decorator;

import org.junit.Test;

public class PrintTest {
    /**
     * 被装饰一次
     */
    @Test
    public void printTest() {
        LogPrinter logPrinter = new LogPrinter();
        LogPrinterDecorater logPrinterDecorater = new LogPrinterDecorater(logPrinter);
        logPrinterDecorater.printlog();
    }

    /**
     * 被装饰2次
     */
    @Test
    public void printTest1() {
        LogPrinter logPrinter = new LogPrinter();
        LogPrinterDecorater logPrinterDecorater = new LogPrinterDecorater(logPrinter);
        LogPrinterDecorater1 logPrinterDecorater1 = new LogPrinterDecorater1(logPrinterDecorater);
        logPrinterDecorater1.printlog();
    }
}
