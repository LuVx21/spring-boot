package org.luvx.api;

import java.math.BigDecimal;

public class TypeCase {

    public static void main(String[] args) {
        System.out.println(2.0 - 1.9);

        System.out.println((new BigDecimal("2.0")).subtract(new BigDecimal("1.9")).doubleValue());
    }
}
