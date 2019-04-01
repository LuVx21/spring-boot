package org.luvx.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @ClassName: org.luvx.common.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/28 15:44
 */
@Component
@Slf4j
@Profile({"dev", "test"})
public class WebLogFilter extends OncePerRequestFilter implements Ordered {

    private int order = Ordered.LOWEST_PRECEDENCE - 8;

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        MDC.clear();
        MDC.put("trade_id", UUID.randomUUID().toString().replaceAll("-", ""));
        ContentCachingRequestWrapper wrapperRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(wrapperRequest, wrapperResponse);

        String urlParams = getRequestParams(request);
        log.info("request params: {}", urlParams);

        String requestBodyStr = getRequestBody(wrapperRequest);
        log.info("request body: {}", requestBodyStr);

        String responseBodyStr = getResponseBody(wrapperResponse);
        log.info("response body: {}", responseBodyStr);

        wrapperResponse.copyBodyToResponse();
    }

    /**
     * 获取请求地址上的参数
     *
     * @param request
     * @return
     */
    public static String getRequestParams(HttpServletRequest request) {
        String type = request.getMethod();
        String uri = request.getRequestURI();
        StringBuilder sb = new StringBuilder(type + " " + uri + " ");
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            sb.append(name + "=").append(request.getParameter(name));
            if (enu.hasMoreElements()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 请求参数
     *
     * @param request
     */
    private String getRequestBody(ContentCachingRequestWrapper request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);

        if (wrapper != null) {
            byte[] buffer = wrapper.getContentAsByteArray();
            String characterEncoding = wrapper.getCharacterEncoding();
            String body = getPayLoad(buffer, characterEncoding);
            return body.replaceAll("\\n", "");
        }
        return "";
    }

    /**
     * 响应参数
     *
     * @param response
     */
    private String getResponseBody(ContentCachingResponseWrapper response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);

        if (wrapper != null) {
            byte[] buffer = wrapper.getContentAsByteArray();
            String characterEncoding = wrapper.getCharacterEncoding();
            String body = getPayLoad(buffer, characterEncoding);
            return body;
        }

        return "";
    }

    /**
     * byte[] -> str
     *
     * @param buf
     * @param characterEncoding
     * @return
     */
    private String getPayLoad(byte[] buf, String characterEncoding) {
        if (buf == null) {
            return "";
        }

        if (buf.length > 0) {
            String payload;
            try {
                payload = new String(buf, 0, buf.length, characterEncoding);
            } catch (UnsupportedEncodingException ex) {
                payload = "[unknown]";
            }
            return payload;
        }
        return "";
    }
}