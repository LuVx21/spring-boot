package org.luvx.boot.redis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@RedisHash
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private long   id;
    @Indexed
    private String userName;
    private String password;
    private int    age;
    private User   friend;

    public static User of(long id, String userName) {
        User user = new User();
        user.id = id;
        user.userName = userName;
        return user;
    }
}
