package org.luvx.type;

import java.util.HashMap;

/**
 * 研究String,包装类的不可变性
 * StringBuilder的可变性
 */
public class UnChangeCase {

    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();
        Integer i = new Integer(888);
        map.put(i, "888");
        System.out.println(map.get(i));// 888
        i = i + 222;
        System.out.println(map.get(i));// null

        HashMap<String, String> map1 = new HashMap<>();
        String str = "aa";
        map1.put(str, "aaa");
        System.out.println(map1.get(str));// aaa
        str = str + "bb";
        System.out.println(map1.get(str));// null

        HashMap<StringBuilder, String> map2 = new HashMap<>();
        StringBuilder sb = new StringBuilder("aa");
        map2.put(sb, "aaa");
        System.out.println(map2.get(sb));// aa
        sb.append("bb");
        System.out.println(map2.get(sb));// aa
    }
}
