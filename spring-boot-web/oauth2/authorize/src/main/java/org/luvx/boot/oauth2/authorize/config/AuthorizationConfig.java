package org.luvx.boot.oauth2.authorize.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.stereotype.Component;

@Component
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    private static String ClientId    = "javayz";
    private static String Security    = "my_secret";
    private static String my_resource = "my_resource";
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 开启允许表单认证
        security.allowFormAuthenticationForClients().checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //client_id
                .withClient(ClientId)
                //secret
                .secret(bCryptPasswordEncoder.encode(Security))
                //授权模式
                .authorizedGrantTypes("authorization_code")
                //作用域，也就是权限范围
                .scopes("all")
                //资源的ID
                .resourceIds(my_resource)
                //回调地址
                .redirectUris("http://localhost:8080/callback");
    }
}
