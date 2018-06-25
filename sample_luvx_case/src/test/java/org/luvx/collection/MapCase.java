package org.luvx.collection;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapCase {

    /**
     * HashMap
     */
    @Test
    public void run01() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("b", "B");
        map.put("a", "A");
        map.put("c", "C");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        map.clear();
        map.putIfAbsent("b", "B");
        map.putIfAbsent("a", "A");
        map.putIfAbsent("c", "C");
        map.forEach((id, val) -> System.out.println(val));
    }

    /**
     * LinkedHashMap
     */
    @Test
    public void run02() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("b", "B");
        map.put("a", "A");
        map.put("c", "C");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

}
