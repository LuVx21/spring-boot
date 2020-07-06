package org.luvx.entity.pojo;

import com.github.pagehelper.Page;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.luvx.entity.User;

@Data
@Builder
public class UserModel {
    @Tolerate
    public UserModel() {
    }

    private Long       id;
    private String     userName;
    private String     password;
    private Integer    age;
    private Page<User> page;
}
