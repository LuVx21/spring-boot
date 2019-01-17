package org.luvx.api.classload;

public class ClassLoadCase {
    static class A {
        public void method() {
            System.out.println("AAAAA");
        }
    }

    static class B extends A {
        public void method() {
            System.out.println("BBBBB");
        }
    }

    public static void method(A a) {
        System.out.println("111111111");
        a.method();
    }

    public static void method(B b) {
        System.out.println("222222222");
        b.method();
    }

    public static void main(String[] args) {
        ClassLoadCase _case = new ClassLoadCase();
        A a = new B();
        _case.method(a);
    }
}
