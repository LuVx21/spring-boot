package org.luvx.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DBConfig {
    private String host;
    private String post;
    private String username;
    private String password;
    private boolean isValid;
}
