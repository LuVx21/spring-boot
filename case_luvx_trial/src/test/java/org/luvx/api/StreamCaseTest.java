package org.luvx.api;

import org.junit.Test;

import java.io.*;

/**
 * 文件I/O操作
 * 文件的传输都是以字节流进行
 * 开发中常用字节流
 *
 * @author renxie
 */
public class StreamCaseTest {

    File directory = new File("");
    String courseFile;

    {
        try {
            // 项目绝对路径
            courseFile = directory.getCanonicalPath() + File.separator + "test.txt";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字节流
     * 不使用缓冲区,直接操作文件
     *
     * @throws IOException IO异常
     */
    @Test
    public void run01() throws IOException {
        File f = new File(courseFile);
        OutputStream out = new FileOutputStream(f);
        byte b[] = "hello world".getBytes();
        out.write(b);
        // 此处不关闭,也会在文件中写入,所以字节流是直接操作文件本身
        out.close();
    }

    /**
     * 字符流
     * 缓冲区:一段特殊的内存
     * 针对频繁操作的文件,直接操作性能会很低,因为读取内存速度比较快
     * 所以将这个文件读入缓冲区进行操作以提高性能
     *
     * @throws IOException IO异常
     */
    @Test
    public void run02() throws IOException {
        File f = new File(courseFile);
        Writer writer = new FileWriter(f);
        writer.write("\n hello world!");
        // 清空缓冲区,此操作下即使不关闭流也能写入
        // writer.flush();
        // 此处若不关闭,不会写入文件,可以判断字符流操作时使用了缓冲区
        // 关闭字符流的时会把缓冲区内容进行输出
        writer.close();
    }

}
