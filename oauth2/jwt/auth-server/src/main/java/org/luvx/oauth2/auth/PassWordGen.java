package org.luvx.oauth2.auth;

import java.util.stream.Stream;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassWordGen {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Stream.of("1121",
                        "client_app_id",
                        "admin",
                        "client"
                )
                .map(s -> s + "=" + encoder.encode("1121"))
                .forEachOrdered(System.out::println);
    }
}
