package org.luvx.java7;

import java.io.*;

public class Demo1 {

    static String firstLineOfFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }
    // catch
    static String firstLineOfFile(String path, String defaultVal) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        } catch (IOException e) {
            return defaultVal;
        }
    }

    static void copy(String src, String dst) throws IOException {
        try (InputStream in = new FileInputStream(src); OutputStream out = new FileOutputStream(dst)) {
            byte[] buf = new byte[1024];
            int n;
            while ((n = in.read(buf)) >= 0)
                out.write(buf, 0, n);
        }
    }

    public static void main(String[] args) throws IOException {
        String path = "D:\\code\\me_repo\\spring-boot-case\\src\\main\\resources\\application.properties";
        System.out.println(firstLineOfFile(path));

        String src = "D:\\code\\me_repo\\spring-boot-case\\src\\main\\resources\\application.properties";
        String dst = "D:\\code\\me_repo\\spring-boot-case\\src\\main\\resources\\application.properties1";
        copy(src, dst);
    }


}
