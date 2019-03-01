package org.luvx;

import org.junit.Test;

import java.io.*;

/**
 * @ClassName: org.luvx
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/21 20:34
 */
public class WriteFileTest {

    private static final String filePath = "E:\\2.txt";

    @Test
    public void method1() {
        try (PrintStream ps = new PrintStream(new FileOutputStream(new File(filePath)))) {
            ps.println("foo ");
            ps.append("bar ");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void method2() {
        /// try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)))) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.append("foo \\r\\n");
            bw.write(" abc \\r\\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void method3() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath), true)) {
            pw.println("abc ");
            pw.println("hef ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void method4() {
        try (RandomAccessFile randomFile = new RandomAccessFile(filePath, "rw")) {
            randomFile.seek(randomFile.length());
            randomFile.writeBytes("foo" + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void method5() {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write("foo".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void method6() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length = -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
