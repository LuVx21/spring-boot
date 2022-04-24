package org.luvx.boot.oauth2.controller;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TokenCheck {
    private List<String> aud;
    private String       user_name;
    private List<String> scope;
    private boolean      active;
    private long         exp;
    private List<String> authorities;
    private String       client_id;
}
