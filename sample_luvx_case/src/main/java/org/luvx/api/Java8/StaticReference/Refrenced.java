package org.luvx.api.Java8.StaticReference;

/**
 * 静态引用
 * 使用::获取方法或者构造函数的引用,既可以是类的也可以是对象的
 */
public class Refrenced {

    private String name;

    public Refrenced() {
    }

    public String getName() {
        return name;
    }

    public Refrenced(String name) {
        this.name = name;
    }

    public String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }

    @Override
    public String toString() {
        return this.name;
    }
}
