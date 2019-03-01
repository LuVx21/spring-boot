package org.luvx;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;

public class FileReadUtils {

    public static String getPath(String path) {
        URL url = FileReadUtils.class.getClassLoader().getResource(path);
        return url.getPath();
    }

    /**
     * 读取文件为String对象
     *
     * @param path 文件路径
     * @return String类型对象
     */
    public static String readFile(String path) {
        File file = new File(path);
        if (StringUtils.isBlank(path) || !file.exists()) {
            return "";
        }

        StringBuilder sb = null;
        try {
            try (BufferedReader bReader = new BufferedReader(new FileReader(file))) {
                sb = new StringBuilder();
                String s = "";
                while ((s = bReader.readLine()) != null) {
                    sb.append(s + System.lineSeparator());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return sb.toString();
        }
    }
}
