package org.luvx.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SpringMVC 中拦截器
 */
// 配置如下
// <mvc:interceptors>
// <mvc:interceptor>
// <mvc:mapping path="/mvc/**"/>
// <bean class="org.luvx.interceptor.MyInterceptor"></bean>
// </mvc:interceptor>
// </mvc:interceptors>

public class SpringMVCInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) {
        System.out.println("LuVx:SpringMVCInterceptor:preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) {
        System.out.println("LuVx:SpringMVCInterceptor:postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) {
        System.out.println("LuVx:SpringMVCInterceptor:afterCompletion");
    }
}