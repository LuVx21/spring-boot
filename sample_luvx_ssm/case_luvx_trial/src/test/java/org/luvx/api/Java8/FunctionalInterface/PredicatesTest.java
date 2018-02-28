package org.luvx.api.Java8.FunctionalInterface;

import org.junit.Test;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * 内置函数式接口
 * 可以通过@FunctionalInterface 标注来对现有接口启用Lambda功能支持
 * http://www.importnew.com/10360.html
 */

public class PredicatesTest {
    @Test
    public void funTest() {
        Predicate<String> predicate = (s) -> s.length() > 0;

        predicate.test("foo");
        predicate.negate().test("foo");

        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
    }
}

