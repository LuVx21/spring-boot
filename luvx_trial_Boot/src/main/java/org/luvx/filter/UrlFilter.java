package org.luvx.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 自定义过滤器
 */
public class UrlFilter implements Filter {
    @Override
    public void init(FilterConfig arg0) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("From UrlFilter,url :" + request.getRequestURI());
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }

}
