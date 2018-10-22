package org.luvx.api.collections;

import org.luvx.entity.User;

import java.util.ArrayList;

/**
 * 20181022 ele.me
 */
public class ListCase {
    // 考察什么?
    public static void method1() {
        ArrayList<Integer> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        for (int i = 0; i < list.size(); i++) {
            if (i == 5) {
                list.remove(list.get(i));
            }
            System.out.println(i);
        }
    }

    // 算坑?
    public static void method2() {
        User user = new User();
        ArrayList<User> list = new ArrayList<>(100);

        for (int i = 0; i < 100; i++) {
            user.setId(i);
            list.add(user);
        }

        for (int i = 0; i < 100; i++) {
            System.out.println(list.get(i).getId());
        }
    }

    public static void main(String[] args) {
        method1();
    }
}
