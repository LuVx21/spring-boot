package org.luvx.utils;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class Tools {
    public static final String NEW_LINE = System.getProperty("line.separator");
    private static Logger logger = Logger.getLogger(Tools.class);
    static ResourceBundle wapResources;
    public static final String UPPER_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    public static final String DIGITALS = "0123456789";

    public Tools() {
        wapResources = null;
    }

    public static String getDirectory(String name) {
        if (wapResources == null) {
            wapResources = ResourceBundle.getBundle("wap");
            // wapResources = ResourceBundle.getBundle("conf");
        }
        return delNull(wapResources.getString(name));
    }

    public static String delNull(String str) {
        if (str == null) {
            return "";
        }
        if ((str.trim().equals("null")) || (str.trim().indexOf("null") > 0)) {
            return "";
        }
        return str.trim();
    }

    public static String replace(String text, String find, String replace) {
        if ((text == null) || (find == null) || (replace == null)) {
            return text;
        }
        int findLen = find.length();
        int textLen = text.length();
        if ((textLen == 0) || (findLen == 0)) {
            return text;
        }
        StringBuffer sb = new StringBuffer();
        int begin = 0;
        int i = text.indexOf(find);
        while (i != -1) {
            sb.append(text.substring(begin, i));
            sb.append(replace);
            begin = i + findLen;
            i = text.indexOf(find, begin);
        }
        if (begin < textLen) {
            sb.append(text.substring(begin));
        }
        return sb.toString();
    }

    public static String replace(String original, String[][] character) {
        if ((original == null) || (character == null) || (character.length == 0)) {
            return original;
        }
        for (int i = 0; i < character.length; i++) {
            if (original.indexOf(character[i][0]) != -1) {
                original = replace(original, character[i][0], character[i][1]);
            }
        }
        return original;
    }

    public static String getClasspathResource(String path)
            throws FileNotFoundException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        try {
            if (url == null) {
                throw new FileNotFoundException(path);
            }
            return URLDecoder.decode(url.getFile(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("something wrong!", e);
        }
        return null;
    }

    public static String readFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(NEW_LINE);
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void writeFile(String content, String pathname) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(pathname));
            bufferedWriter.write(content);
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static final Random RANDOM = new Random();

    public static String generalRandomString(int length, boolean upperLetter, boolean lowerLetter, boolean digital) {
        StringBuilder sourceBuilder = new StringBuilder();
        if (upperLetter) {
            sourceBuilder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }
        if (lowerLetter) {
            sourceBuilder.append("abcdefghijklmnopqrstuvwxyz");
        }
        if (digital) {
            sourceBuilder.append("0123456789");
        }
        int sourceLength = sourceBuilder.length();
        for (int i = 0; i < length; i++) {
            sourceBuilder.append(sourceBuilder.charAt(RANDOM.nextInt(sourceLength)));
        }
        return sourceBuilder.substring(sourceLength);
    }


    public static String getSignData(Map<String, String> params) {
        StringBuffer content = new StringBuffer();

        List<String> keys = new java.util.ArrayList(params.keySet());
        java.util.Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            if ((!"sign".equals(key)) && (!"sign_type".equals(key))) {

                String value = (String) params.get(key);
                if (value != null) {
                    content.append((i == 0 ? "" : "&") + key + "=" + value);
                } else {
                    content.append((i == 0 ? "" : "&") + key + "=");
                }
            }
        }

        return content.toString();
    }
}