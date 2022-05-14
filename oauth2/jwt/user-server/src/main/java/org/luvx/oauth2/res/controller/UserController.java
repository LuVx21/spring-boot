package org.luvx.oauth2.res.controller;

import static org.luvx.oauth2.res.config.AccessTokenConfig.SIGNING_KEY;

import java.nio.charset.StandardCharsets;
import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {

    @GetMapping("/headPic")
    // @PreAuthorize("hasAuthority('ROLE_admin')")
    // @PreAuthorize("hasAnyRole('ROLE_admin')")
    public String admin(Authentication authentication) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getCredentials();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String token = details.getTokenValue();
        Claims claims = Jwts.parser()
                .setSigningKey(SIGNING_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        String userName = claims.get("user_name").toString();
        return "admin".equals(userName)
               ? "https://github.githubassets.com/favicons/favicon.svg"
               : "https://himg.bdimg.com/sys/portrait/item/pp.1.38395b45.ySiBjY8BPHzQpyjZBYVlCQ.jpg";
    }


    @GetMapping("/user")
    public Principal getCurrentUser(Principal principal) {
        log.info("principal:{}", principal);
        return principal;
    }
}
