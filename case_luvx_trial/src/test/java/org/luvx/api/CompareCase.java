package org.luvx.api;

import org.junit.Test;

/**
 * Comparable 和 Comparator 的区别
 * 在类中实现Comparable,重写compareTo(),可以使用某些操作排序方法进行排序
 * 当一个不可继承的类的对象元素进行排序,使用Comparator,重写compare()
 * <p>
 * Comparable 是排序接口；若一个类实现了 Comparable 接口，就意味着 “该类支持排序”。
 * Comparator 是比较器；我们若需要控制某个类的次序，可以建立一个 “该类的比较器” 来进行排序。
 * 前者应该比较固定，和一个具体类相绑定，而后者比较灵活，它可以被用于各个需要比较功能的类使用。可以说前者属于 “静态绑定”，而后者可以 “动态绑定”。
 * 我们不难发现：Comparable 相当于 “内部比较器”，而 Comparator 相当于 “外部比较器”。
 */
public class CompareCase {
    @Test
    public void run0() {
        Comparable comparable;
    }
}
