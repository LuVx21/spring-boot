package org.luvx.retry.retry02;

import lombok.extern.slf4j.Slf4j;
import org.luvx.retry.common.RetryConstant;
import org.luvx.retry.common.Task;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.function.Consumer;

/**
 * @author: Ren, Xie
 * @desc: jdk动态代理方式实现
 * 和简单实现的方式没有啥区别, 只是对原有代码侵入小
 * 具有jdk 动态代理使用的局限性 -> 使用字节码技术解决
 */
@Slf4j
public class Retry02 implements InvocationHandler {
    private Object obj;
    static  int    no = 0;

    public Object getProxy(Object obj) {
        this.obj = obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        while (no < RetryConstant.MAX_TIMES) {
            try {
                if ("accept".equals(method.getName())) {
                    return method.invoke(obj, args);
                }
            } catch (Exception e) {
                no++;
                log.info("第{}次执行({})", no, LocalDateTime.now());
                try {
                    Thread.sleep(no * 1_000);
                } catch (InterruptedException ee) {
                }
                if (no >= RetryConstant.MAX_TIMES) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Consumer t = (Consumer) new Retry02().getProxy(new Task());
        t.accept(0);
    }
}
