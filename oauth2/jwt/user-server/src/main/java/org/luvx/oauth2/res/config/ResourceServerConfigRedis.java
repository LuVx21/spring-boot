// package org.luvx.oauth2.res.config;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
// import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
// import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
// import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
// import org.springframework.security.oauth2.provider.token.TokenStore;
// import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
// @Configuration
// @EnableResourceServer
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// public class ResourceServerConfigRedis extends ResourceServerConfigurerAdapter {
//
//     @Value("${security.oauth2.client.client-id}")
//     private String clientId;
//
//     @Value("${security.oauth2.client.client-secret}")
//     private String secret;
//
//     @Value("${security.oauth2.authorization.check-token-access}")
//     private String checkTokenEndpointUrl;
//
//     @Autowired
//     private RedisConnectionFactory redisConnectionFactory;
//
//     @Bean
//     public TokenStore redisTokenStore() {
//         return new RedisTokenStore(redisConnectionFactory);
//     }
//
//     @Bean
//     public RemoteTokenServices tokenService() {
//         RemoteTokenServices tokenService = new RemoteTokenServices();
//         tokenService.setClientId(clientId);
//         tokenService.setClientSecret(secret);
//         tokenService.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
//         return tokenService;
//     }
//
//     @Override
//     public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//         resources.tokenServices(tokenService());
//     }
// }
