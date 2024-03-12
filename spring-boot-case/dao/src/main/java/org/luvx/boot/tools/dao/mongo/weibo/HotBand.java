package org.luvx.boot.tools.dao.mongo.weibo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
@Document("weibo_hot_band")
@NoArgsConstructor
@AllArgsConstructor
public class HotBand {
    @MongoId
    private Long id;

    private String                 word;
    private Map<LocalDate, String> rankMap;
    private String                 category;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HotBand that = (HotBand) o;
        return Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }
}
