package org.luvx.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "user")
public class User {
    private static final long serialVersionUID = 1L;

    @Tolerate
    public User() {
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;
    @Column(nullable = false, unique = true)
    private String  userName;
    private String  password;
    private Integer age;
}