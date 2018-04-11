package org.luvx.api.Java8.StaticReference;

/**
 * 静态引用
 */
public class Refrence {

    private String name;

    public Refrence() {
    }

    public Refrence(String name) {
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
