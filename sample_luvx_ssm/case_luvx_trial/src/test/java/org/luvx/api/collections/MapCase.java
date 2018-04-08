package org.luvx.api.collections;

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
