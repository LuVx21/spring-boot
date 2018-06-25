package org.luvx.api.Java8.Lambda;

import org.luvx.api.Java8.StaticReference.Refrenced;
import org.junit.Test;

public class ConvertableTest {
    @Test
    public void convertTest() {
        // 实现接口
        Convertable<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);
    }

    @Test
    public void convertTest1() {
        Convertable<String, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("1234");
        System.out.println(converted);
    }

    /**
     * 静态引用:对象普通方法
     */
    @Test
    public void funTest() {
        Refrenced something = new Refrenced();
        Convertable<String, String> converter = something::startsWith;

        String converted = converter.convert("Java");
        System.out.println(converted);
    }

    /**
     * 静态引用:构造方法
     */
    @Test
    public void funTest1() {
        Convertable<String, Refrenced> converter = Refrenced::new;
        Refrenced ref = converter.convert("Java");
        System.out.println(ref);
    }

}
