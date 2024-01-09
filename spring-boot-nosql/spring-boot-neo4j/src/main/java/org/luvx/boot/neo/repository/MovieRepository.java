package org.luvx.boot.neo.repository;

import org.luvx.boot.neo.entity.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieRepository extends Neo4jRepository<Movie, UUID> {
    Movie findByTitle(String title);
}