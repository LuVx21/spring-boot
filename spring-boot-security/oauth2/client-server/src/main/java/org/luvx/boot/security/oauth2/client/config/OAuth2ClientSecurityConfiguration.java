package org.luvx.boot.security.oauth2.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * OAuth2.0 Client 配置
 *
 * @author felord.cn
 */
@EnableWebSecurity(debug = true)
public class OAuth2ClientSecurityConfiguration {


    /**
     * 放开对{@code redirect_uri}的访问，否则会出现{@code 403}，授权服务器需要回调该地址
     *
     * @param httpSecurity the http security
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    SecurityFilterChain oauth2ClientSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/foo/bar").anonymous()
                .anyRequest().authenticated()
                .and()
                .oauth2Client();
        return httpSecurity.build();
    }
}
