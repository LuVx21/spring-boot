package org.luvx.api;

import org.junit.Test;


public class Log4jCaseTest {

    @Test
    public void helloTest() {
        System.out.println("---------------------------");
        Log4jCase.hello();
        System.out.println("---------------------------");
    }
}