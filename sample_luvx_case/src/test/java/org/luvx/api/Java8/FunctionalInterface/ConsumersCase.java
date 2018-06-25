package org.luvx.api.Java8.FunctionalInterface;

import java.util.function.Consumer;

import org.junit.Test;
import org.luvx.api.Java8.StaticReference.Refrenced;

/**
 * 消费者接口
 *
 */
public class ConsumersCase {
    @Test
    public void run01() {
        Consumer<Refrenced> refrenced = (r) -> System.out.println("Hello, " + r.getName());
        refrenced.accept(new Refrenced("World"));
    }
}
