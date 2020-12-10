package org.luvx.java;

import org.junit.Test;

public class JTest {
    @Test
    public void bTest() {
        J j = new J();
        System.out.println(
                j.method("world")
        );
    }
}