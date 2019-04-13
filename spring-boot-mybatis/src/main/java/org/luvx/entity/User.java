package org.luvx.entity;

import lombok.*;
import org.luvx.common.BaseQueryEntity;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends BaseQueryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long          id;
    private String        userName;
    private String        password;
    private Integer       age;
    /**
     * 一对一
     */
    private Article       article;
    /**
     * 一对多
     */
    private List<Article> articles;
}