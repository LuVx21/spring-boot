package org.luvx.boot.neo.repository;

import org.luvx.boot.neo.entity.Movie;
import org.luvx.boot.neo.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {
    Person findByName(String name);

    @Query("MATCH (a:Person)-[:`参演`]->(b:Movie) WHERE a.name = $name RETURN b")
    List<Movie> findMovieByActor(@Param("name") String name);
}
