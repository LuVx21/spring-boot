package org.luvx.api;

/**
 * 枚举类实际是一个普通类
 * 继承自java.lang.Enum,类本身是final的,即不可被继承
 * 每个枚举值则是该类的实例,并且是static final的,
 * 这也是可以用于实现单例模式的原因
 *
 * @author renxie
 */
public enum EnumCase {
    MON, TUE, WED, THU, FRI, SAT, SUN;
}