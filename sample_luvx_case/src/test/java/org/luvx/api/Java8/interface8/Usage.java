package org.luvx.api.Java8.interface8;

import org.junit.Before;
import org.junit.Test;
import org.luvx.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.*;

public class Usage {

    private List<User> users = new ArrayList<>();

    @Before
    public void init() {
        users.add(new User(10L, "LuVx", "1", 20));
        users.add(new User(11L, "LuVx", "2", 20));
        users.add(new User(20L, "foo", "3", 20));
        users.add(new User(22L, "bar", "4", 20));
    }

    @Test
    public void method() {
        users.forEach((User user) -> System.out.println(user.getUserName()));
        users.stream().filter(u -> Objects.equals(u.getUserName(), "LuVx")).forEach(System.out::println);
    }

    @Test
    public void method1() {
        users.stream().filter(u -> Objects.equals(u.getUserName(), "LuVx")).map(user -> "LuVx").forEach(System.out::println);
    }

    @Test
    public void method2() {
        long count = users.stream().filter(u -> Objects.equals(u.getUserName(), "LuVx")).map(user -> "LuVx").count();
        System.out.println(count);
    }

    @Test
    public void method3() {
        Consumer<List<User>> counter = list -> {
            int size = list.size();
            System.out.println(size);
        };
        counter.accept(users);
    }

    @Test
    public void method4() {
        Function<String, Integer> length = s -> s.length();
        int l = length.apply("LuVx");
        System.out.println(l);
    }

    @Test
    public void method5() {
        BiFunction<List<User>, String, Long> count =
                (list, filter) -> list.stream().filter(u -> Objects.equals(u.getUserName(), "LuVx")).count();
        long c = count.apply(users, "LuVx");
        System.out.println(c);
    }

    @Test
    public void method6() {
        Supplier<String> constValueSupplier = () -> "LuVx";
        String str = constValueSupplier.get();
        System.out.println(str);
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
}
