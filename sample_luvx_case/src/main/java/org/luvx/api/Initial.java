package org.luvx.api;

/**
 * 类初始化顺序
 * 静态代码:按照声明顺序加载
 * 父类构造函数
 * 变量初始化:无初始值则为null
 * 实例代码块:和变量初始化按顺序加载
 * 子类构造方法
 */
public class Initial {
    public static String staticField = "静态属性";

    {
        System.out.println("普通代码块1");
    }

    public String normalField = "普通属性";
    public String returnValue = getStrFromMethod();

    static {
        System.out.println("静态代码块");
        // 此代码块移动到staticField声明前会报错
        System.out.println(staticField);
    }

    // 此代码块不能移动到普通属性前
    {
        System.out.println("普通代码块2");
        System.out.println(normalField);
        System.out.println(returnValue);
    }

    public Initial() {
        System.out.println("构造方法");
    }

    public String getStrFromMethod() {
        System.out.println("普通方法");
        System.out.println(normalField);
        System.out.println(returnValue);
        return "普通方法返回值";
    }
}