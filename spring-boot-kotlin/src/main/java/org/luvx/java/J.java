package org.luvx.java;

import lombok.extern.slf4j.Slf4j;
import org.luvx.kotlin.K;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class J {
    public String method(String s) {
        log.info("参数:{}", s);
        return "hello " + s + " from java!";
    }

    public static void main(String[] args) {
        K k = new K();
        log.info("in java:{}" + k.method("kotlin"));
    }
}
