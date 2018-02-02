package org.luvx.commons;

import org.junit.Test;


public class JacksonUtilsTest {

    @Test
    public void objectToJsonTest() {
        String string = "hello world";
        String json = JacksonUtils.objectToJson(string);
        System.out.println(json);
    }

}