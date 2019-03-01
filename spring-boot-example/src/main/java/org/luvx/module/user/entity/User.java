package org.luvx.module.user.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.luvx.common.base.BaseQueryEntity;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class User extends BaseQueryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long          userId;
    private String        userName;
    private String        passWord;
    private Integer       age;
}