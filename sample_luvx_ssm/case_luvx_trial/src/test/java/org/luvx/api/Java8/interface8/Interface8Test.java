package org.luvx.api.Java8.interface8;

import org.junit.Test;

public class Interface8Test {


    class Formulaimpl implements Formula {
        @Override
        public double calculate(int a) {
            return sqrt(a * 100);
        }
    }


    @Test
    public void formulaTest() {
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };

        System.out.println(formula.calculate(100));
        System.out.println(formula.sqrt(16));
    }

    //    lambda表达式无法使用函数式接口的默认方法,下述代码编译出错
    //    Formula formula = (a) -> sqrt( a * 100);
}
