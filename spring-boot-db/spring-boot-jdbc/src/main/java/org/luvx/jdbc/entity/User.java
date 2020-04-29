package org.luvx.jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @ClassName: org.luvx.jdbc
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/21 9:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Long    id;
    private String  userName;
    private String  password;
    private Integer age;
}
