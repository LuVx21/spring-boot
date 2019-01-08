package org.luvx.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class UserLog {
    private String username;
    private String userid;
    private String state;
}
