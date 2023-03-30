package org.luvx.boot.web.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class RestTemplateUtils {

    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate a) {
        restTemplate = a;
    }

    /**
     * 发起请求
     *
     * @param url
     * @param method
     * @param request
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T request(String url, HttpMethod method, HttpEntity request, Class<T> clazz) {
        ResponseEntity<T> response = restTemplate.exchange(url, method, request, clazz);
        HttpStatus status = response.getStatusCode();
        if (status.is2xxSuccessful()) {
            T t = response.getBody();
            return t;
        } else {
            throw new RuntimeException("请求失败:" + url);
        }
    }

    /**
     * 设置请求实体
     *
     * @param map key 为 {@code org.springframework.http.HttpHeaders}的常量
     * @return
     */
    public static HttpEntity<String> httpEntity(Map<String, String> map) {
        HttpHeaders headers = new HttpHeaders();
        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Map.Entry<String, String> entry : set) {
            headers.add(entry.getKey(), entry.getValue());
        }

        String body = map.get("body");
        HttpEntity<String> request = new HttpEntity(body, headers);
        return request;
    }
}
