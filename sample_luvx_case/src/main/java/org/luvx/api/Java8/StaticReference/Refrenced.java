package org.luvx.api.Java8.StaticReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 静态引用
 * 使用::获取方法或者构造函数的引用,既可以是类的也可以是对象的
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Refrenced {
    private String name;

    public static String toLowerCase(String s) {
        return s.toLowerCase();
    }

    public String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }
}
