package org.luvx.api.Java8.FunctionalInterface;

import java.util.Optional;

import org.junit.Test;

/**
 * 防止NullPointerException的手段
 *
 */
public class OptionalsCase {

    @Test
    public void run01() {
        Optional<String> optional = Optional.of("bam");

        optional.isPresent(); // true
        optional.get(); // "bam"
        optional.orElse("fallback"); // "bam"

        optional.ifPresent((s) -> System.out.println(s.charAt(0)));
    }

}
