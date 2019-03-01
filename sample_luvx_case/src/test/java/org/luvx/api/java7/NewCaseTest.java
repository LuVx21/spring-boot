package org.luvx.api.java7;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystemAlreadyExistsException;

public class NewCaseTest {

    /**
     * switch中使用String
     */
    @Test
    public void method0() {
        String str = "LuVx";
        switch (str) {
            case "foo":
                System.out.println("foo");
                break;
            case "LuVx":
                System.out.println("LuVx");
                break;
            default:
                System.out.println("default");
                break;
        }
    }

    /**
     * 1. try-with-resources: 适用于任何实现了java.lang.AutoCloseable接口的类
     * 2. 同时捕捉多个异常
     */
    @Test
    public void method2() {
        try {
            try (OutputStream fos = new FileOutputStream("E:\\1.txt")) {
                System.out.println(fos);
            }
        } catch (FileSystemAlreadyExistsException | IOException e) {
            System.out.println(e);
        }
    }

    /**
     * 长数字可用分隔符以直观
     */
    @Test
    public void method3() {
        int i = 10_0000;
        System.out.println(i + 1);
    }
}
