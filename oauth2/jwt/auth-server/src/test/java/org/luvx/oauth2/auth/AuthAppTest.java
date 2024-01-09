package org.luvx.oauth2.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class AuthAppTest {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("1121"));
    }
}