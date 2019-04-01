package org.luvx.guava;

import com.google.common.base.*;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: org.luvx.guava
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/24 20:32
 */
public class BaseTest {
    /*
    6666666666666666666666666666666666666666666666
    6699996669999666666666669999666999666666699966
    6669966996666996666666996666996696666666669666
    6669966996666669966996666669966696666666669666
    6669966699666666699666666699666696666666669666
    6669966669966666666666666996666696666666669666
    6669966666996666666666669966666696666666669666
    6669966666699666666666699666666696666666669666
    6669966666669996666669996666666696666666669666
    6669966666666999666699966666666669966666996666
    6669966666666699966999666666666666699699666666
    6699996666666666699666666666666666666966666666
    6666666666666666666666666666666666666666666666
    */

    @Test
    public void method0() {
        // 普通Collection
        List<String> list = Lists.newArrayList();
        Set<String> set = Sets.newHashSet();
        Map<String, String> map = Maps.newHashMap();

        // 不变Collection
        ImmutableList<String> iList = ImmutableList.of("a", "b", "c");
        ImmutableSet<String> iSet = ImmutableSet.of("e1", "e2");
        ImmutableMap<String, String> iMap = ImmutableMap.of("k1", "v1", "k2", "v2");

        String listStr = Joiner.on("-").join(iList);
        System.out.println(listStr);

        String mapStr = Joiner.on(",").withKeyValueSeparator(":").join(iMap);
        System.out.println(mapStr);
    }

    /**
     * 特殊集合
     */
    @Test
    public void method1() {
    }

    /**
     * String -> 集合
     */
    @Test
    public void method2() {
        String str = "1-2-3-4-5-6";
        List<String> list = Splitter.on("-").splitToList(str);
        list.forEach(System.out::println);

        str = "1-2-3-4-  5-  6   ";
        list = Splitter.on("-").omitEmptyStrings().trimResults().splitToList(str);
        System.out.println(list);

        String input = "aa.dd,,ff,,.";
        List<String> result = Splitter.onPattern("[.|,]").omitEmptyStrings().splitToList(input);
        System.out.println(result);


        // 判断匹配结果
        boolean s0 = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).matches('K'); //true
        // 保留数字文本
        String s1 = CharMatcher.digit().retainFrom("abc 123 efg");
        // 删除数字文本
        String s2 = CharMatcher.digit().removeFrom("abc 123 efg");

        str = "xiaoming=11,xiaohong=23";
        Map<String, String> map = Splitter.on(",").withKeyValueSeparator("=").split(str);
        System.out.println(map);
    }

    @Test
    public void method3() {
        //按照条件过滤
        ImmutableList<String> names = ImmutableList.of("begin", "code", "Guava", "Java");
        Iterable<String> fitered = Iterables.filter(names, Predicates.or(Predicates.equalTo("Guava"), Predicates.equalTo("Java")));
        System.out.println(fitered); // [Guava, Java]

        ImmutableMap<String, Integer> m = ImmutableMap.of("begin", 12, "code", 15);
        Map<String, Integer> m2 = Maps.transformValues(m, new Function<Integer, Integer>() {
            public Integer apply(Integer input) {
                if (input > 12) {
                    return input;
                } else {
                    return input + 1;
                }
            }
        });

        System.out.println(m2);   // {begin=13, code=15}
    }

    /**
     * set 交,并,差集
     */
    @Test
    public void method4() {
        HashSet setA = Sets.newHashSet(1, 2, 3, 4, 5);
        HashSet setB = Sets.newHashSet(4, 5, 6, 7, 8);

        // 并集
        Sets.SetView<Integer> union = Sets.union(setA, setB);
        // union.forEach(System.out::println);
        // 差集 A - B = {1,2,3}
        Sets.SetView<Integer> difference = Sets.difference(setA, setB);
        difference.forEach(System.out::println);
        // 交集
        Sets.SetView<Integer> intersection = Sets.intersection(setA, setB);
        // intersection.forEach(System.out::println);
    }

    /**
     * map 交,并,差集
     */
    @Test
    public void method5() {
        ImmutableMap<String, String> mapA = ImmutableMap.of("k1", "v1", "k2", "v2");
        ImmutableMap<String, String> mapB = ImmutableMap.of("k2", "v2", "k3", "v3");

        MapDifference<String, String> differenceMap = Maps.difference(mapA, mapB);
        System.out.println(differenceMap);
        boolean diff = differenceMap.areEqual();

        Map<String, MapDifference.ValueDifference<String>> entriesDiffering = differenceMap.entriesDiffering();
        System.out.println(entriesDiffering);
        Map<String, String> entriesOnlyOnLeft = differenceMap.entriesOnlyOnLeft();
        Map<String, String> entriesOnlyOnRight = differenceMap.entriesOnlyOnRight();
        Map<String, String> entriesInCommon = differenceMap.entriesInCommon();
        System.out.println(entriesInCommon);
    }
}
