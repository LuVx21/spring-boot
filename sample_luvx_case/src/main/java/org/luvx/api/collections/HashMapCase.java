package org.luvx.api.collections;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * map遍历，
 */
public class HashMapCase {

    private static final Map<String, String> map = new ConcurrentHashMap<>();

    /**
     * Entry遍历
     */
    public static void read() {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key = " + entry.getKey() + ",Value = " + entry.getValue());
        }
    }

    /**
     * keySet遍历
     */
    public static void read0() {
        for (String key : map.keySet()) {
            System.out.println("key = " + key + ",Value = " + map.get(key));
        }
    }

    /**
     * 迭代器遍历
     */
    public static void read1() {
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println("key = " + entry.getKey() + ",Value = " + entry.getValue());
        }
    }


    public static String intern(String str) {
        String result = map.get(str);
        if (result == null) {
            result = map.putIfAbsent(str, str);
            if (result == null)
                result = str;
        }
        return result;
    }

    public static String intern0(String str) {
        String proviousValue = map.putIfAbsent(str, str);
        return proviousValue == null ? str : proviousValue;
    }

    public static void main(String[] args) {
        // map.put("ren", "xie");
        String str = intern0("ren");
        System.out.println(str);

    }
}
