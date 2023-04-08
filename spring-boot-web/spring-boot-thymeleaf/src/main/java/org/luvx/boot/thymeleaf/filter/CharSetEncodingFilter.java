package org.luvx.boot.thymeleaf.filter;

import org.springframework.core.annotation.Order;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@Order(1)
@WebFilter(urlPatterns = "/*",filterName = "CharSetEncodingFilter")
public class CharSetEncodingFilter implements Filter {
    private static final String ENCODING_UTF_8 = "UTF-8";

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("123132132132132132132131312");

        request.setCharacterEncoding(ENCODING_UTF_8);
        response.setCharacterEncoding(ENCODING_UTF_8);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
