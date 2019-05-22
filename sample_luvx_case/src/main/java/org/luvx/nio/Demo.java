package org.luvx.nio;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @ClassName: org.luvx.nio
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/17 17:48
 */
public class Demo {

    public static void main(String[] args) {
        final Path path = Paths.get("E:\\logs");
        System.out.println(path);
    }
}
