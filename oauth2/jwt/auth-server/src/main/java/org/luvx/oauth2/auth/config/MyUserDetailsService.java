package org.luvx.oauth2.auth.config;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Map<String, Tuple2<String, String>> userInfo = ImmutableMap.of(
            "admin", new Tuple2<>("1121", "ROLE_ADMIN"),
            "user", new Tuple2<>("1121", "ROLE_ADMIN")
    );

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username is:{}", username);
        Tuple2<String, String> tuple = userInfo.get(username);
        String password = passwordEncoder.encode(tuple._1);
        List<SimpleGrantedAuthority> authorities = Lists.newArrayList(new SimpleGrantedAuthority(tuple._2));
        return new User(username, password, authorities);
    }
}
