package org.luvx.entity;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long    id;
    private String  userName;
    private String  password;
    private Integer age;
}