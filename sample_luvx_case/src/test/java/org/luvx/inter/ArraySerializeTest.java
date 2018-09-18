package org.luvx.inter;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ArraySerializeTest {
    Map<String, String> map1 = new HashMap<>();
    Map<String, String> map2 = new HashMap<>();
    Map<String, String> map3 = new HashMap<>();
    private Map[] array = {map1, map2, map3};
    String result = null;

    @Before
    public void initMap() {
        for (int i = 0; i < 10; i++) {
            map1.put(getRandomString(), getRandomString());
            map2.put(getRandomString(), getRandomString());
            map3.put(getRandomString(), getRandomString());
        }
    }

    public String getRandomString() {
        int start = 0x0000;
        int end = 0x00ff;
        Random random = new Random();
        char[] chars = new char[10];
        for (int i = 0; i < 10; i++) {
            int code = random.nextInt(end - start + 1) + start;
            char c = (char) code;
            chars[i] = (c == '\n' || c == ';' || c == '=') ? ' ' : c;
            // chars[i] = c;
        }
        return new String(chars);
    }

    @Test
    public void storeTest() {
        result = ArraySerialize.store(array);
        System.out.println(result);
    }

    @Test
    public void loadTest() {
        result = ArraySerialize.store(array);
        System.out.println(result);
        System.out.println("--------------");
        Map<String, String>[] array = null;
        array = ArraySerialize.load(result);
        for (Map<String, String> map : array)
            System.out.println(map);
    }
}