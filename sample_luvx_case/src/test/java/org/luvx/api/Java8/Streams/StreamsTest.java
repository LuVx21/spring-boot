package org.luvx.api.Java8.Streams;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StreamsTest {
    List<String> list = new ArrayList<String>();

    @Before
    public void init() {
        list.add("ddd2");
        list.add("aaa2");
        list.add("bbb1");
        list.add("aaa1");
        list.add("bbb3");
        list.add("ccc");
        list.add("bbb2");
        list.add("ddd1");
    }

    @Test
    public void run01() {
        list.stream().sorted().filter((s) -> s.startsWith("a")).forEach(System.out::println);
    }

    @Test
    public void run02() {
        list.stream().map(String::toUpperCase).sorted((a, b) -> b.compareTo(a)).forEach(System.out::println);
    }

    @Test
    public void run03() {
        boolean anyStartsWithA = list.stream().anyMatch((s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA);

        boolean allStartsWithA = list.stream().allMatch((s) -> s.startsWith("a"));
        System.out.println(allStartsWithA);

        boolean noneStartsWithZ = list.stream().noneMatch((s) -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);
    }

    @Test
    public void run04() {
        long startsWithB = list.stream().filter((s) -> s.startsWith("b")).count();
        System.out.println(startsWithB);
    }

    @Test
    public void run05() {
        Optional<String> reduced = list.stream().sorted().reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);

        // Collection.parallelStream()
    }
}
