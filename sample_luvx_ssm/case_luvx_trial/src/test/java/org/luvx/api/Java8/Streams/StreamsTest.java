package org.luvx.api.Java8.Streams;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StreamsTest {
    @Test
    public void funtest() {
        List<String> stringCollection = new ArrayList<String>();

        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

        stringCollection.stream().filter((s) -> s.startsWith("a")).forEach(System.out::println);

        stringCollection.stream().sorted().filter((s) -> s.startsWith("a")).forEach(System.out::println);

        stringCollection.stream().map(String::toUpperCase).sorted((a, b) -> b.compareTo(a)).forEach(System.out::println);

        boolean anyStartsWithA = stringCollection.stream().anyMatch((s) -> s.startsWith("a"));

        System.out.println(anyStartsWithA);

        boolean allStartsWithA = stringCollection.stream().allMatch((s) -> s.startsWith("a"));

        System.out.println(allStartsWithA);

        boolean noneStartsWithZ = stringCollection.stream().noneMatch((s) -> s.startsWith("z"));

        System.out.println(noneStartsWithZ);

        long startsWithB = stringCollection.stream().filter((s) -> s.startsWith("b")).count();

        System.out.println(startsWithB);

        Optional<String> reduced = stringCollection.stream().sorted().reduce((s1, s2) -> s1 + "#" + s2);

        reduced.ifPresent(System.out::println);

        //Collection.parallelStream()
    }
}
