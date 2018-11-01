package org.luvx.type;

public class TypeCase1 {
    private int i01 = 42;
    private Integer i = 42;
    private Long l = 42l;
    private Double d = 42.0;

    /**
     * JVM对除了浮点型之外的包装类都提供了类似于String这样的常量池机制（不过范围仅仅是-128到127之间）
     */
    public void method0() {
        System.out.println(i01 == i);
    }

    /**
     * 包装类的“==”运算在不遇到算术运算的情况下不会自动拆箱
     * 包装类的equals()方法不处理数据转型
     */
    public void method1() {
        // System.out.println(i == l);
        // System.out.println(i == d);
        // System.out.println(l == d);

        System.out.println(i.equals(l));// false
        System.out.println(i.equals(d));// false
        System.out.println(l.equals(d));// false
        System.out.println(d.equals(l));// false
        System.out.println(l.equals(42L));// true
    }
    
    public static void main(String[] args) {
        TypeCase1 case1 = new TypeCase1();
        case1.method0();
    }
}
