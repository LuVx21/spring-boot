package org.luvx.inter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ArraySerialize {
    /**
     * 把数组保存到一个字符串中
     *
     * @param array 待序列化Map型数组
     * @return 字符串的存储结构
     */
    public static String store(Map<String, String>[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(mapAssemble(array[i]) + (i != array.length - 1 ? "\n" : ""));
        }
        return sb.toString();
    }

    /**
     * 把字符串中的内容读取为字典数组
     *
     * @param str 字符串的存储结构
     * @return Map型数组
     */
    public static Map<String, String>[] load(String str) {
        if (str == null || str.length() == 0) {
            return new Map[0];
        }
        // String[] array = str.split("\\n");
        String[] array = split(str, "\\n");

        int length = array.length;
        Map<String, String>[] result = new Map[length];

        for (int i = 0; i < length; i++) {
            // String[] array1 = array[i].split(";");
            String[] array1 = split(array[i], ";");
            Map<String, String> map = new HashMap<>();
            for (int j = 0; j < array1.length; j++) {
                // map.put(array1[j].split("=")[0], array1[j].split("=")[1]);
                map.put(split(array1[j], "=")[0], split(array1[j], "=")[1]);
            }
            result[i] = map;
        }
        return result;
    }

    /**
     * 将Map组装成`k1=v1;k2=v2`格式
     *
     * @return 组装结果
     */
    public static String mapAssemble(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            sb.append(entry.getKey() + "=" + entry.getValue() + (iterator.hasNext() ? ";" : ""));
        }
        return sb.toString();
    }

    public static String[] split(String str, String regex) {
        String[] result = null;
        if (str.length() <= regex.length()) {
            return new String[0];
        } else {
            result = new String[str.length() / (regex.length() + 1) + 1];
        }

        char[] strChars = str.toCharArray();
        char[] regexChars = (regex == "\\n") ? new char[]{'\n'} : regex.toCharArray();
        int startIndex = 0;
        int resultIndex = 0;

        for (int i = 0; i < strChars.length; i++) {
            int j = 0;
            while (i + j < strChars.length && j < regexChars.length && strChars[i + j] == regexChars[j]) {
                j++;
            }

            if (j > 0 && i != startIndex && j == regexChars.length) {
                result[resultIndex++] = new String(strChars, startIndex, i - startIndex);
                startIndex = i + regexChars.length;
            }
        }
        result[resultIndex++] = new String(strChars, startIndex, strChars.length - startIndex);
        return Arrays.copyOf(result, resultIndex);
    }
}
