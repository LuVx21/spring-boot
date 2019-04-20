package org.luvx.api.java8.lambda.jdk;

import org.junit.Test;
import org.luvx.entity.User;

import java.util.Objects;
import java.util.function.*;

/**
 * jdk自带的一些函数式接口
 * java.util.function包
 */
public class FunctionsCase {

    @Test
    public void run00() {
        Function<String, Integer> toInteger = Integer::valueOf;
        Integer integer = toInteger.apply("123");

        // 函数链
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        String str = backToString.apply("123");
    }

    @Test
    public void run01() {
        Supplier<User> personSupplier = User::new;
        User user = personSupplier.get();
        System.out.println(user);
    }

    @Test
    public void run02() {
        Consumer<String> consumer = r -> System.out.println("Hello, " + r);
        consumer.accept("World");
    }

    @Test
    public void method5() {
        BiFunction<String, String, Integer> count = (str1, str2) -> (str1 + str2).length();
        int c = count.apply("foo", "bar");
        System.out.println(c);
    }

    @Test
    public void method7() {
        UnaryOperator<Integer> square = i -> i * i;
        int result = square.apply(9);
        System.out.println(result);
    }

    @Test
    public void method8() {
        BinaryOperator<Integer> sum = (a, b) -> a + b;
        int result = sum.apply(1, 2);
        System.out.println(result);
    }

    @Test
    public void method9() {
        Predicate<String> isStr = type -> type.equalsIgnoreCase("LuVx");
        System.out.println(isStr.test("F.LuVx"));
        System.out.println(isStr.test("LuVx"));
    }

    /**
     * 内置函数式接口
     * 可以通过@FunctionalInterface 标注来对现有接口启用Lambda功能支持
     */
    @Test
    public void run03() {
        Predicate<String> predicate = s -> s.length() > 0;

        System.out.println("长度>0: " + predicate.test("foo"));
        System.out.println("长度<=0: " + predicate.negate().test("foo"));

        Predicate<Object> nonNull = Objects::nonNull;
        Predicate<Object> isNull = Objects::isNull;

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
        System.out.println("不为空: " + isNotEmpty.test("foo"));
    }
}
