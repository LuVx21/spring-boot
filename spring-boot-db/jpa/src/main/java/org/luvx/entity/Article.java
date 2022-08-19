package org.luvx.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "article")
public class Article {
    private static final long serialVersionUID = 1L;

    @Tolerate
    public Article() {
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long          id;
    private String        articleName;
    private LocalDateTime createTime;
    private Long          userId;
}