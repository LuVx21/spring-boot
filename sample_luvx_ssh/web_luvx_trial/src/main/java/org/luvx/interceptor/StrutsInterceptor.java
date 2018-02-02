package org.luvx.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Struts中拦截器
 */
public class StrutsInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        System.out.println("LuVx:StrutsInterceptor:Before Action...");
        String result = invocation.invoke();
        System.out.println("LuVx:StrutsInterceptor:After Action...");
        return result;
    }
}