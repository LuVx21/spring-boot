package org.luvx.api.java8.Streams;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsTest {
    ImmutableList<String> list = ImmutableList.of(
            "ddd2",
            "aaa2",
            "bbb1",
            "aaa1",
            "bbb3",
            "ccc",
            "bbb2",
            "ddd1"
    );

    /**
     * 流只能消费一次
     */
    @Test
    public void run00() {
        Stream<String> stream = list.stream();
        // 排序
        stream = stream.sorted(Comparator.reverseOrder());
        // 过滤
        stream = stream.filter(s -> s.startsWith("bb"));
        /// allMatch noneMatch
        // boolean b = stream.anyMatch(s -> s.startsWith("bb"));
        // 映射
        stream = stream.map(s -> "bbb1~3");

        // long i = stream.count();

        // stream.forEach(System.out::println);
    }

    @Test
    public void run05() {
        Optional<String> reduced = list.stream().reduce((s1, s2) -> s1 + "," + s2);
        reduced.ifPresent(System.out::println);

        String str = list.stream().collect(Collectors.joining(","));

        // list.parallelStream();
    }
}
