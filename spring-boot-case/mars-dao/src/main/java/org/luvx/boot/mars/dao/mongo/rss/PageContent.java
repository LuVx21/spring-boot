package org.luvx.boot.mars.dao.mongo.rss;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Document("rss_feed")
@NoArgsConstructor
@AllArgsConstructor
public class PageContent {
    @MongoId
    private Long          id;
    @Field("spider_key")
    private String        spiderKey;
    private String        url;
    private String        title;
    private String        pubDate;
    private Set<String>   categorySet;
    private List<String>  content;
    private int           invalid;
    private LocalDateTime createTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageContent that = (PageContent) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
