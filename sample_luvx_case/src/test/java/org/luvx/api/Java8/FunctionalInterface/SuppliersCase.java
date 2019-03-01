package org.luvx.api.Java8.FunctionalInterface;

import java.util.function.Supplier;

import org.junit.Test;

/**
 * 生产者接口
 *
 */
public class SuppliersCase {

    @Test
    public void run01() {
        Supplier<String> personSupplier = String::new;
        personSupplier.get();
    }
}
