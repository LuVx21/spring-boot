package org.luvx.api.Java8.Lambda;


/**
 * 声明函数式接口
 * @FunctionalInterface 不添加也是正确的
 * 保证接口中有且仅有一个抽象方法声明即可
 * 可以有任意默认方法(default)
 */

@FunctionalInterface
public interface Convertable<F,T> {
    T convert(F from);
}
