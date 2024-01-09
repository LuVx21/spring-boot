package org.luvx.boot.neo.entity;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Collection;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node
@Getter
@ToString
public class Person {
    @Id
    private Long   id;
    private String name;

    public Person(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Relationship(type = "参演", direction = OUTGOING)
    private Set<Movie> actors = Sets.newHashSet();

    @Relationship(type = "执导", direction = OUTGOING)
    private Set<Movie> directors = Sets.newHashSet();

    public void addActors(Collection<Movie> movie) {
        actors.addAll(movie);
    }

    public void addDirectors(Collection<Movie> movie) {
        directors.addAll(movie);
    }
}
