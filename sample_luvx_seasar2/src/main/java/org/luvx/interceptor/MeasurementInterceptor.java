package org.luvx.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;

/**
 * 用于计算调用方法所耗费的时间
 */
public class MeasurementInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        //建立一个100长度的StringBuffer
        StringBuffer buf = new StringBuffer(100);
        long start = 0;
        long end = 0;
        //获取当前调用的类类名称
        buf.append(getTargetClass(invocation).getName());
        buf.append("#");

        //调用的方法名称
        buf.append(invocation.getMethod().getName());
        buf.append("(");

        //执行的参数
        Object[] args = invocation.getArguments();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; ++i) {
                buf.append(args[i]);
                buf.append(", ");
            }
            buf.setLength(buf.length() - 2);
        }
        buf.append(")");
        try {
            start = System.currentTimeMillis();
            //执行传入的方法
            Object ret = invocation.proceed();
            //系统毫秒数
            end = System.currentTimeMillis();
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(buf.toString() + "花费时间" + (end - start));
        }
        return null;
    }
}
