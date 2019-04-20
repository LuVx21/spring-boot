package org.luvx.api.java8.lambda.jdk;

import org.junit.Test;
import org.luvx.entity.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Comparator
 */
public class ComparatorsCase {

    @Test
    public void run00() {
        List<String> names = Arrays.asList("peter", "anna", "xenia", "mike");

        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.compareTo(b);
            }
        };

        comparator = (String a, String b) -> {
            return b.compareTo(a);
        };

        comparator = (String a, String b) -> b.compareTo(a);
        comparator = (a, b) -> a.compareTo(b);
        comparator = Comparator.comparing(String::toString);

        /// 逆序有reverseOrder()方法
        comparator = Comparator.naturalOrder();

        // names.sort(comparator);
        Collections.sort(names, comparator);
        System.out.println(names);
    }

    /**
     * 自定义对象
     */
    @Test
    public void run01() {
        Comparator<User> comparator = (p1, p2) -> p1.getUserName().compareTo(p2.getUserName());
        comparator = Comparator.comparing(User::getUserName);

        User p1 = User.builder().userName("John").build();
        User p2 = User.builder().userName("Alice").build();
        // +
        System.out.println(comparator.compare(p1, p2));
        // -
        System.out.println(comparator.reversed().compare(p1, p2));
    }
}
