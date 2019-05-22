package org.luvx.guava;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: org.luvx.guava
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/27 10:05
 */
public class CollectionTest {

    /**
     * key 重复时直接覆盖,不抛出异常
     * value 重复时抛出IllegalArgumentException
     * inverse后修改会形象原map
     */
    @Test
    public void method0() {
        // 双向Map(Bidirectional Map) 键与值都不能重复
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("foo", "bar");
        biMap.put("A", "a");
        System.out.println(biMap.get("A"));

        BiMap<String, String> map = ImmutableBiMap.of("foo", "bar", "A", "a");
        System.out.println(map.get("foo"));
        System.out.println(map.inverse().get("bar"));
    }

    /**
     * key可重复map
     */
    @Test
    public void method1() {
        // key可以重复
        Multimap<String, Integer> map = ArrayListMultimap.create();
        map.put("aa", 1);
        map.put("aa", 2);
        // [1, 2]
        System.out.println(map.get("aa"));
    }

    /**
     * 双键Map
     */
    @Test
    public void method2() {
        Table<String, String, Integer> tables = HashBasedTable.create();
    }

    /**
     * 无序+可重复 Set
     */
    @Test
    public void method3() {
        Multiset<String> set = HashMultiset.create();
    }

    /**
     * 分割List
     */
    @Test
    public void method4() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        List<List<String>> temp = Lists.partition(list, 2);
        System.out.println(temp);
        // 更改子串会影响原来的
        temp.get(1).add("d");

        Iterable<List<String>> temp1 = Iterables.partition(list, 2);
        System.out.println(temp1);
    }
}
