package org.luvx;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: org.luvx
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/21 20:34
 */
public class ReadFileTest {
    private static final String filePath = "E:\\2.txt";

    @Test
    public void method1() {
        /// try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))))) {
        try (BufferedReader bReader = new BufferedReader(new FileReader(new File(filePath)))) {
            String s;
            while ((s = bReader.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void method2() {
        try (FileReader fr = new FileReader(new File(filePath))) {
            int ch;
            while ((ch = fr.read()) != -1) {
                System.out.print((char) ch + "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void method3() {
        try (FileInputStream in = new FileInputStream(new File(filePath))) {
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String str = new String(buffer, "utf8");
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void method4() {
        try (FileInputStream in = new FileInputStream(new File(filePath))) {
            byte[] buffer = new byte[1024];
            while ((in.read(buffer) != -1)) {
                String str = new String(buffer, "utf8");
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void method5() {
        try (RandomAccessFile randomFile = new RandomAccessFile(filePath, "rw")) {
            String str;
            while ((str = randomFile.readLine()) != null) {
                str = new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
