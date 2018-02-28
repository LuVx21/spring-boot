package org.luvx.api.Java8.Lambda;

import org.luvx.api.Java8.StaticReference.Refrence;
import org.junit.Test;


public class FuncInterfaceTest {
    @Test
    public void convertTest() {
        // 实现接口
        FuncInterface<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);
    }

    @Test
    public void convertTest1() {
        FuncInterface<String, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("1234");
        System.out.println(converted);
    }

    @Test
    public void funTest() {
        Refrence something = new Refrence();
        FuncInterface<String, String> converter = something::startsWith;

        String converted = converter.convert("Java");
        System.out.println(converted);
    }

    @Test
    public void funTest1() {
        FuncInterface<String, Refrence> converter = Refrence::new;
        Refrence ref = converter.convert("Java");
        System.out.println(ref);
    }


}
