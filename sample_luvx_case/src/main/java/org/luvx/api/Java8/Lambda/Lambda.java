package org.luvx.api.Java8.Lambda;

public class Lambda {

// lambda 表达式只能引用 final 或 final 局部变量
// 不能在内部修改外部的局部变量,能否只读????
// 匿名内部类中使用类外的变量,那么此变量需要final修饰


// 使用的接口必须是函数式接口(其中有且仅有一个抽象方法的接口)
// @FunctionalInterface 注解将接口声明为函数式接口
/*
interface MathOperation {
    int operation(int a, int b);
    default int addition(int a, int b){
    return a + b;
    }
}
*/


    public interface MathOperation {
        int operation(int a, int b);
    }

    public interface GreetingService {
        void sayMessage(String message);
    }

    public int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
}