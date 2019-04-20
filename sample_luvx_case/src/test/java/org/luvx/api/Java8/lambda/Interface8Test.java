package org.luvx.api.java8.lambda;

import org.junit.Test;

import java.util.function.Function;

/**
 * 函数式接口使用
 */
public class Interface8Test {
    /**
     * 定义一个函数式接口
     */
    @FunctionalInterface
    public interface Formula {
        int plusOne(int a);

        default int plusSelf(int a) {
            return a + a;
        }
    }

    /**
     * java8以前可以定义类实现接口
     */
    class Formulaimpl implements Formula {
        @Override
        public int plusOne(int a) {
            return a + 1;
        }
    }

    @Test
    public void formulaTest() {
        // java8以前可以使用局部内部类
        Formula formula = new Formula() {
            @Override
            public int plusOne(int a) {
                return a + 1;
            }
        };
        // java8后使用lambda的方式实现
        formula = (a) -> a + 1;

        System.out.println(formula.plusOne(100) + " " + formula.plusSelf(100));
    }

    /**
     * Lambda的变量访问
     * Variable used in lambda expression should be final or effectively final
     */
    @Test
    public void testScopes() {
        String b = "a";
        // b = "b";// 放开编译出错
        Function<String, String> function = from -> from.concat(b);
    }
}
