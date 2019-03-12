package org.luvx.api.classload;

/**
 * @ClassName: org.luvx.api.classload
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 16:57
 */
public class ClassObjectCase {
    public void me() {
        System.out.println(this);
    }

    public static void main(String[] args) {
        ClassObjectCase case1 = new ClassObjectCase();
        ClassObjectCase case2 = new ClassObjectCase();
        case1.me();
        case2.me();
    }
}
