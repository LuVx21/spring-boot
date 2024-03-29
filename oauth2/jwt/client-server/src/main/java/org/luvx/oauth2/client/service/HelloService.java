package org.luvx.oauth2.client.service;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpMethod.GET;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HelloService {
    public static final String SIGNING_KEY = "abcdefg";

    private String access_token  = "";
    private String refresh_token = "";

    @Autowired
    private RestTemplate restTemplate;

    public void getData(String code, Model model) {
        log.info("token:{}, code:{}", access_token, code);
        if (isBlank(access_token) && !isBlank(code)) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("code", code);
            map.add("client_id", "client-jwt");
            map.add("client_secret", "1121");
            map.add("redirect_uri", "http://localhost:8082/login");
            map.add("grant_type", "authorization_code");
            Map<String, Object> resp = restTemplate.postForObject("http://localhost:8080/oauth/token", map, Map.class);
            String r = resp.entrySet().stream()
                    .map(e -> e.getKey() + "=" + e.getValue().toString())
                    .collect(Collectors.joining("\n"));
            log.info("获取 token 的响应:{}", r);
            access_token = resp.get("access_token").toString();
            refresh_token = resp.get("refresh_token").toString();
        }
        Claims claims = Jwts.parser()
                .setSigningKey(SIGNING_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(access_token)
                .getBody();
        String userName = claims.get("user_name").toString();
        model.addAttribute("username", userName);
        model.addAttribute("accessToken", access_token);
        model.addAttribute("headPic", loadDataFromResServer());
    }

    private String loadDataFromResServer() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + access_token);
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> entity =
                    restTemplate.exchange("http://localhost:8081/headPic", GET, httpEntity, String.class);
            return entity.getBody();
        } catch (RestClientException e) {
            log.info("get resource error:{}", e.getMessage());
            return "异常:获取资源服务内容";
        }
    }

    @Scheduled(cron = "0 55 0/1 * * ？")
    public void tokenTask() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "client-jwt");
        map.add("client_secret", "1121");
        map.add("refresh_token", refresh_token);
        map.add("grant_type", "refresh_token");
        Map<String, String> resp = restTemplate.postForObject("http://localhost:8080/oauth/token", map, Map.class);
        access_token = resp.get("access_token");
        refresh_token = resp.get("refresh_token");
    }

    public void logout() {
        access_token = refresh_token = "";
    }
}
