package org.luvx.api.Java8.FunctionalInterface;

import java.util.Comparator;

import org.junit.Test;
import org.luvx.api.Java8.StaticReference.Refrenced;

public class ComparatorsCase {

    @Test
    public void run01() {
        Comparator<Refrenced> comparator = (p1, p2) -> p1.getName().compareTo(p2.getName());

        Refrenced p1 = new Refrenced("John");
        Refrenced p2 = new Refrenced("Alice");
        // +
        System.out.println(comparator.compare(p1, p2));
        // -
        System.out.println(comparator.reversed().compare(p1, p2));
    }
}
