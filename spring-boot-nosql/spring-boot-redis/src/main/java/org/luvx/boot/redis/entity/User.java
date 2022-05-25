package org.luvx.boot.redis.entity;

// import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

// @Entity
@Getter
@Setter
@ToString
@RedisHash
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private long   id;
    // @Column(nullable = false, unique = true)
    @Indexed
    private String userName;
    // @Column(nullable = false)
    private String password;
    // @Column(nullable = false)
    private int    age;

    public User(String userName, String password, int age) {
        this.userName = userName;
        this.password = password;
        this.age = age;
    }
}
