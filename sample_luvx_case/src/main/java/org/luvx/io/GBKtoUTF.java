package org.luvx.io;

import java.io.*;

/**
 * GBK的文本文件转为UTF-8文件
 */
public class GBKtoUTF {

    public static void changeCode(String from, String to) throws Exception {
        File file = new File(from);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        String line = null;
        while ((line = br.readLine()) != null) {
            if (!line.trim().equals("")) {
            }
        }
        // write
        File filename = new File("xxx");
        filename.createNewFile();
        FileWriter fw = new FileWriter(filename);

        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "GBk")));
        // out.write(hexToString(search_result));
        out.flush();
        out.close();
    }



}
