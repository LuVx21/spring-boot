package org.luvx.api.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapCase {

    private static final Map<String, String> map = new ConcurrentHashMap<>();

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
}
