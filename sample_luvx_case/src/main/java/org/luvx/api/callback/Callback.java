package org.luvx.api.callback;


/**
 * 回调机制的要素:
 * - 接口
 * - 接口实现:被调用者使用后,完成功能,在back回调用者
 * - 调用者:使用上述接口中的方法
 * 例:
 * 小明有道难题找小红解决,小红解决后写到小明的本子上
 * 小刚也有难题,也想让小红解决后写在自己本子上,则实现Caller接口即可
 * 体育老师....数学也不好,真丢人,退群吧
 */

public interface Callback {
    void fix(String question, Caller caller);
}
