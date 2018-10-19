package org.luvx.pattern.creational.Singleton;

import org.junit.Test;

public class SingletonTest {

    @Test
    public void createTest() {
        Singleton2 instance = Singleton2.getInstance();
        Singleton2 instance1 = Singleton2.getInstance();
        System.out.println(instance == instance1);
    }

    @Test
    public void createTest1() {
        Singleton1 instance = Singleton1.getInstance();
        Singleton1 instance1 = Singleton1.getInstance();
        System.out.println(instance == instance1);
    }

    @Test
    public void createTest3() {
        System.out.println(Singleton3.INSTANCE == Singleton3.INSTANCE);
    }

}
