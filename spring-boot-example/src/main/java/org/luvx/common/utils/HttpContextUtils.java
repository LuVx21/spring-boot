package org.luvx.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: org.luvx.common.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/28 11:33
 */
public class HttpContextUtils {
    private static ServletRequestAttributes servletRequestAttributes;

    static {
        servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest getHttpServletRequest() {
        return servletRequestAttributes.getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        return servletRequestAttributes.getResponse();
    }
}
