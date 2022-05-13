package org.luvx.boot.oauth2.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

@RestController
public class IndexController {
    /**
     * <pre>
     *     {
     *       "aud": [
     *         "my_resource"
     *       ],
     *       "user_name": "root",
     *       "scope": [
     *         "all"
     *       ],
     *       "active": true,
     *       "exp": 1650831241,
     *       "authorities": [
     *         "/*"
     *       ],
     *       "client_id": "javayz"
     *     }
     * </pre>
     */
    @PostMapping(value = {"/oauth/check_token"})
    public Object index(String token) throws JsonProcessingException {
        TokenCheck tokenCheck = new TokenCheck();
        tokenCheck.setAud(Lists.newArrayList("my_resource"));
        tokenCheck.setUser_name("root");
        tokenCheck.setScope(Lists.newArrayList("all"));
        tokenCheck.setActive(true);
        tokenCheck.setExp(LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.ofHours(8)));
        tokenCheck.setAuthorities(Lists.newArrayList("/*"));
        tokenCheck.setClient_id("javayz");
        return (new ObjectMapper()).writeValueAsString(token);
    }
}
