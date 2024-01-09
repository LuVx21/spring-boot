package org.luvx.boot.neo.entity;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;

@Node("Movie")
@Getter
@ToString
public class Movie {

    @Id
    @GeneratedValue
    private UUID   id;
    @Property("title")
    private String title;
    @Property("tagline")
    private String description;

    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Relationship(type = "参演", direction = INCOMING)
    private Set<Person> actors = Sets.newHashSet();

    @Relationship(type = "执导", direction = INCOMING)
    private Set<Person> directors = Sets.newHashSet();

    public void addActors(Collection<Person> u) {
        actors.addAll(u);
    }

    public void addDirectors(Collection<Person> u) {
        directors.addAll(u);
    }
}
