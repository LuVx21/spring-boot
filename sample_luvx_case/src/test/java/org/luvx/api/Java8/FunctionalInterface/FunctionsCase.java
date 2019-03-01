package org.luvx.api.Java8.FunctionalInterface;

import java.util.function.Function;

import org.junit.Test;

public class FunctionsCase {

    @Test
    public void run01() {
        Function<String, Integer> toInteger = Integer::valueOf;
        Integer integer = toInteger.apply("123");

        // 函数链
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        String str = backToString.apply("123");
    }
}
