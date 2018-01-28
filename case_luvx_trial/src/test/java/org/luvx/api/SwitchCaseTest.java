package org.luvx.api;

import org.junit.Test;

public class SwitchCaseTest {

    /**
     * 支持的数据类型:
     * byte、short、int、char、String,枚举
     */
    @Test
    public void run0() {
        // int i = 1;
        String i="aaa";
        switch (i) {
            case "aaa":
                System.out.println("True");
        }

    }
}
