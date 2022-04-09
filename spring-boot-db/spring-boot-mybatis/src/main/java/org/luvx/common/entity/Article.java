package org.luvx.common.entity;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Article extends BaseQueryEntity {
    private Long id;
    private String articleName;
    private LocalDate createTime;
    private Long userId;
}