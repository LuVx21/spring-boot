package org.luvx.api.java8.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

/**
 * 静态引用
 */
public class ConvertibleTest {
    @FunctionalInterface
    public interface Convertible<F, T> {
        T convert(F from);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Refrenced {
        private String name = "foobar";

        public static String toLowerCase(String s) {
            return s.toLowerCase();
        }

        public String toUpperCase(String s) {
            return s.toUpperCase();
        }

        public String captureName() {
            char[] array = name.toCharArray();
            array[0] -= 32;
            return String.valueOf(array);
        }
    }

    /**
     * 静态引用:对象普通方法
     */
    @Test
    public void funTest() {
        Convertible<String, String> converter = Refrenced::toLowerCase;
        String converted = converter.convert("Java");
        System.out.println(converted);

        Refrenced something = new Refrenced();
        converter = something::toUpperCase;
        converted = converter.convert("Java");
        System.out.println(converted);

        Convertible<String, Refrenced> converter2 = Refrenced::new;
        Refrenced ref = converter2.convert("Java");
        System.out.println(ref);

        Convertible<Refrenced, String> converter1 = Refrenced::captureName;
        converted = converter1.convert(something);
        System.out.println(converted);
    }
}
