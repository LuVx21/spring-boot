package org.luvx.boot.thymeleaf.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(1)
@WebFilter(urlPatterns = "/*",filterName = "UrlFilter")
public class UrlFilter implements Filter {
    @Override
    public void init(FilterConfig config) {
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
