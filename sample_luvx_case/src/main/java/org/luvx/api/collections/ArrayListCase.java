package org.luvx.api.collections;

import org.luvx.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 20181022 ele.me
 */
public class ArrayListCase {

    /**
     * 边遍历边删除尽量用迭代器
     */
    public static void method1() {
        ArrayList<String> aList = new ArrayList<>();
        aList.add("a");
        aList.add("ab");
        aList.add("abc");
        // 这一个使用for遍历不会被remove掉，
        // 因为remove第3个时，会将下面的6个往前移，
        // 第4个的下标变为2，此时i已经变为3
        aList.add("abc");
        aList.add("abcr");
        aList.add("abc");
        aList.add("abcf");
        aList.add("abc");
        aList.add("abdc");

        // 使用for循环边遍历边remove，第二个abc不会被删除
        for (int i = 0; i < aList.size(); i++) {
            if (aList.get(i).equals("abc")) {
                aList.remove(i);
            }
        }
        System.out.println(aList);

        // 可以考虑从后往前遍历并删除
        /*
        for (int i = aList.size() - 1; i >= 0; i--) {
            if (aList.get(i).equals("abc")) {
                aList.remove(i);
            }
        }
        System.out.println(aList);
        */

        // 增强for循环边遍历边删除会抛ConcurrentModificationException异常
       /*
        for (String str : aList) {
            if (str.equals("abc"))
                aList.remove(str);
        }
        */

        // 使用迭代器边遍历边remove，不会出现上述情况
        /*
        Iterator<String> iterator = aList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals("abc")) {
                iterator.remove();
            }
        }
        System.out.println(aList);
        */
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

    public static void method3() {
        List<String> list = new ArrayList<>(100);
        list.add("aa");
        list.add(null);
        list.add("bb");
        list.add("cc");
        list.add(null);

        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if ("bb".equals(str)) {
                System.out.println(list);
                list.set(i, null);
                System.out.println(list);
            }
        }

        // list.removeAll(Collections.singleton(null));
        // System.out.println(list);
    }

    public static void main(String[] args) {
        method3();
    }
}
