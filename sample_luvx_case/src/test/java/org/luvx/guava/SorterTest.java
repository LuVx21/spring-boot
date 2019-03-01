package org.luvx.guava;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.junit.Test;
import org.luvx.entity.User;

import java.util.Comparator;

/**
 * @ClassName: org.luvx.guava
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/2/3 11:26
 */
public class SorterTest {
    /**
     * 排序
     */
    private Ordering<String> byLengthOrdering = new Ordering<String>() {
        public int compare(String left, String right) {
            return Ints.compare(left.length(), right.length());
        }
    };

    /**
     * 比较
     */
    private Comparator<User> comparator = new Comparator<User>() {
        @Override
        public int compare(User left, User right) {
            return ComparisonChain.start()
                    .compare(left.getUserName(), right.getUserName())
                    .compare(left.getPassword(), right.getPassword())
                    .result();
        }
    };

    @Test
    public void method1() {
        User user1 = new User("foo1", "bar", 15);
        User user2 = new User("foo", "bar", 15);

        int result = comparator.compare(user1, user2);
        System.out.println(result);
    }

    @Test
    public void method2() {


    }

    @Test
    public void method3() {
    }

    @Test
    public void method4() {
    }

    @Test
    public void method5() {
    }

    @Test
    public void method6() {
    }

}
