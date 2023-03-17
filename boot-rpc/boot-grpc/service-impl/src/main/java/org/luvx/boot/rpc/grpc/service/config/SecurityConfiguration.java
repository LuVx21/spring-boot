package org.luvx.boot.rpc.grpc.service.config;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.util.Collections.singletonList;

@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class SecurityConfiguration {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return username -> {
            log.debug("Searching user: {}", username);
            List<SimpleGrantedAuthority> authorities = Collections.emptyList();
            switch (username) {
                case "guest" -> {
                }
                case "user" -> authorities = singletonList(new SimpleGrantedAuthority("ROLE_GREET"));
                default -> throw new UsernameNotFoundException("Could not find user!");
            }
            return new User(username, passwordEncoder.encode(username + "Password"), authorities);
        };
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthenticationProvider) {
        List<AuthenticationProvider> providers = Lists.newArrayList(daoAuthenticationProvider);
        return new ProviderManager(providers);
    }

    @Bean
    GrpcAuthenticationReader authenticationReader() {
        return new BasicGrpcAuthenticationReader();

        // List<GrpcAuthenticationReader> readers = Lists.newArrayList(
        //         new BasicGrpcAuthenticationReader(),
        //         new SSLContextGrpcAuthenticationReader()
        // );
        // return new CompositeGrpcAuthenticationReader(readers);
    }
}
